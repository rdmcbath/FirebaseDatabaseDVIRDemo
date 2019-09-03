package com.mcbath.rebecca.firebasedatabasedvirtest1;

import android.content.Context;
import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.model.value.TimestampValue;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.Dvir;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.Inspection;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Created by Rebecca McBath
 * on 2019-08-30.
 */
public class AddDvirActivity extends AppCompatActivity {
	private static final String TAG = "AddDvirActivity";

	private final String COLLECTION_KEY = "dvirs";

	private LinearLayout brakesFailLayout, connectionsFailLayout, couplingsFailLayout, engineFailLayout, exteriorFailLayout, interiorFailLayout, lightsFailLayout, lockingPinsFailLayout, tieDownsFailLayout, wheelsFailLayout, windshieldFailLayout;
	private EditText brakesCommentsTv, connectionsCommentsTv, couplingsCommentsTv, engineCommentsTv, exteriorCommentsTv, interiorCommentsTv, lightsCommentsTv, lockingPinsCommentsTv, tiedownsCommentsTv, wheelsCommentsTv, windshieldCommentsTv;
	private EditText userName, mobileName, mobileId;
	private Button brakesPassButton, brakesFailButton, connectionsPassButton, connectionsFailButton, couplingsPassButton, couplingsFailButton, enginePassButton, engineFailButton, exteriorPassButton, exteriorFailButton, lightsPassButton, lightsFailButton;
	private Button interiorPassButton, interiorFailButton, lockingPinsPassButton, lockingPinsFailButton, tieDownsPassButton, tieDownsFailButton, wheelsPassButton, wheelsFailButton, windshieldPassButton, windshieldFailButton;
	private ImageView brakesFailIv, connectionsFailIv, couplingsFailIv, engineFailIv, exteriorFailIv, interiorFailIv, lightsFailIv, lockingPinsFailIv, tieDownsFailIv, wheelsFailIv, windshieldFailIv;
	private Dvir dvir;
	private Inspection inspection;
	private FirebaseFirestore mFirestore;
	private DocumentReference mDvirRef;
	private FirebaseAuth mFirebaseAuth;
	private boolean brakesPass, connectionsPass, couplingsPass, enginePass, exteriorPass, interiorPass, lightsPass, lockingPinsPass, tieDownsPass, wheelsPass, windshieldPass, passButtonClicked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_dvir);

		brakesFailLayout = findViewById(R.id.brakesFailLayout);
		connectionsFailLayout = findViewById(R.id.connectionsFailLayout);
		couplingsFailLayout = findViewById(R.id.couplingsFailLayout);
		engineFailLayout = findViewById(R.id.engineFailLayout);
		exteriorFailLayout = findViewById(R.id.exteriorFailLayout);
		interiorFailLayout = findViewById(R.id.interiorFailLayout);
		lightsFailLayout = findViewById(R.id.lightsFailLayout);
		lockingPinsFailLayout = findViewById(R.id.lockingPinsFailLayout);
		tieDownsFailLayout = findViewById(R.id.tieDownsFailLayout);
		wheelsFailLayout = findViewById(R.id.wheelsFailLayout);
		windshieldFailLayout = findViewById(R.id.windshieldFailLayout);
		brakesCommentsTv = findViewById(R.id.brakesCommentsTextView);
		connectionsCommentsTv = findViewById(R.id.connectionsCommentsTextView);
		couplingsCommentsTv = findViewById(R.id.couplingsCommentsTextView);
		engineCommentsTv = findViewById(R.id.engineCommentsTextView);
		exteriorCommentsTv = findViewById(R.id.exteriorCommentsTextView);
		interiorCommentsTv = findViewById(R.id.interiorCommentsTextView);
		lightsCommentsTv = findViewById(R.id.lightsCommentsTextView);
		lockingPinsCommentsTv = findViewById(R.id.lockingPinsCommentsTextView);
		tiedownsCommentsTv = findViewById(R.id.tieDownsCommentsTextView);
		wheelsCommentsTv = findViewById(R.id.wheelsCommentsTextView);
		windshieldCommentsTv = findViewById(R.id.windshieldCommentsTextView);
		brakesPassButton = findViewById(R.id.brakesPassButton);
		brakesFailButton = findViewById(R.id.brakesFailButton);
		connectionsPassButton = findViewById(R.id.connectionsPassButton);
		connectionsFailButton = findViewById(R.id.connectionsFailButton);
		couplingsPassButton = findViewById(R.id.couplingsPassButton);
		couplingsFailButton = findViewById(R.id.couplingsFailButton);
		enginePassButton = findViewById(R.id.enginePassButton);
		engineFailButton = findViewById(R.id.engineFailButton);
		exteriorPassButton = findViewById(R.id.exteriorPassButton);
		exteriorFailButton = findViewById(R.id.exteriorFailButton);
		interiorPassButton = findViewById(R.id.interiorPassButton);
		interiorFailButton = findViewById(R.id.interiorFailButton);
		lightsPassButton = findViewById(R.id.lightsPassButton);
		lightsFailButton = findViewById(R.id.lightsFailButton);
		lockingPinsPassButton = findViewById(R.id.lockingPinsPassButton);
		lockingPinsFailButton = findViewById(R.id.lockingPinsFailButton);
		tieDownsPassButton = findViewById(R.id.tieDownsPassButton);
		tieDownsFailButton = findViewById(R.id.tieDownsFailButton);
		wheelsPassButton = findViewById(R.id.wheelsPassButton);
		wheelsFailButton = findViewById(R.id.wheelsFailButton);
		windshieldPassButton = findViewById(R.id.windshieldPassButton);
		windshieldFailButton = findViewById(R.id.windshieldFailButton);
		brakesFailIv = findViewById(R.id.brakesFailImageView);
		connectionsFailIv = findViewById(R.id.connectionsFailImageView);
		couplingsFailIv = findViewById(R.id.couplingsFailImageView);
		engineFailIv = findViewById(R.id.engineFailImageView);
		exteriorFailIv = findViewById(R.id.exteriorFailImageView);
		interiorFailIv = findViewById(R.id.interiorFailImageView);
		lightsFailIv = findViewById(R.id.lightsFailImageView);
		lockingPinsFailIv = findViewById(R.id.lockingPinsFailImageView);
		tieDownsFailIv = findViewById(R.id.tieDownsFailImageView);
		wheelsFailIv = findViewById(R.id.wheelsFailImageView);
		windshieldFailIv = findViewById(R.id.windshieldFailImageView);
		userName = findViewById(R.id.name_editText);
		mobileName = findViewById(R.id.mobileName_editText);
		mobileId = findViewById(R.id.mobileId_editText);
		Toolbar mToolbar = findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		passButtonClicked = false;
	}

	/**
	 * onClick handler - brakes pass
	 */
	public void passBrakes(View v) {
		brakesPassButton.setText("Passed");
		passButtonClicked = true;
		brakesFailButton.setVisibility(View.GONE);
		if (brakesFailLayout.isShown()) {
			Utils.slide_up(this, brakesFailLayout);
			brakesFailLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * onClick handler - brakes fail
	 */
	public void showBrakesFailSection(View v) {
		if (brakesFailLayout.isShown()) {
			Utils.slide_up(this, brakesFailLayout);
			brakesFailLayout.setVisibility(View.GONE);
		} else {
			brakesFailLayout.setVisibility(View.VISIBLE);
			Utils.slide_down(this, brakesFailLayout);
		}
	}

	/**
	 * onClick handler - connections pass
	 */
	public void passConnections(View v) {
		connectionsPassButton.setText("Passed");
		passButtonClicked = true;
		connectionsFailButton.setVisibility(View.GONE);
		if (connectionsFailLayout.isShown()) {
			Utils.slide_up(this, connectionsFailLayout);
			connectionsFailLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * onClick handler - connections fail
	 */
	public void showConnectionsFailSection(View v) {
		if (connectionsFailLayout.isShown()) {
			Utils.slide_up(this, connectionsFailLayout);
			connectionsFailLayout.setVisibility(View.GONE);
		} else {
			connectionsFailLayout.setVisibility(View.VISIBLE);
			Utils.slide_down(this, connectionsFailLayout);
		}
	}

	/**
	 * onClick handler - couplings pass
	 */
	public void passCouplings(View v) {
		couplingsPassButton.setText("Passed");
		passButtonClicked = true;
		couplingsFailButton.setVisibility(View.GONE);
		if (couplingsFailLayout.isShown()) {
			Utils.slide_up(this, couplingsFailLayout);
			couplingsFailLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * onClick handler - couplings fail
	 */
	public void showCouplingsFailSection(View v) {
		if (couplingsFailLayout.isShown()) {
			Utils.slide_up(this, couplingsFailLayout);
			couplingsFailLayout.setVisibility(View.GONE);
		} else {
			couplingsFailLayout.setVisibility(View.VISIBLE);
			Utils.slide_down(this, couplingsFailLayout);
		}
	}

	/**
	 * onClick handler - engine pass
	 */
	public void passEngine(View v) {
		enginePassButton.setText("Passed");
		passButtonClicked = true;
		engineFailButton.setVisibility(View.GONE);
		if (engineFailLayout.isShown()) {
			Utils.slide_up(this, engineFailLayout);
			engineFailLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * onClick handler - engine fail
	 */
	public void showEngineFailSection(View v) {
		if (engineFailLayout.isShown()) {
			Utils.slide_up(this, engineFailLayout);
			engineFailLayout.setVisibility(View.GONE);
		} else {
			engineFailLayout.setVisibility(View.VISIBLE);
			Utils.slide_down(this, engineFailLayout);
		}
	}

	/**
	 * onClick handler - exterior pass
	 */
	public void passExterior(View v) {
		exteriorPassButton.setText("Passed");
		passButtonClicked = true;
		exteriorFailButton.setVisibility(View.GONE);
		if (exteriorFailLayout.isShown()) {
			Utils.slide_up(this, exteriorFailLayout);
			exteriorFailLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * onClick handler - exterior fail
	 */
	public void showExteriorFailSection(View v) {
		if (exteriorFailLayout.isShown()) {
			Utils.slide_up(this, exteriorFailLayout);
			exteriorFailLayout.setVisibility(View.GONE);
		} else {
			exteriorFailLayout.setVisibility(View.VISIBLE);
			Utils.slide_down(this, exteriorFailLayout);
		}
	}

	/**
	 * onClick handler - interior pass
	 */
	public void passInterior(View v) {
		interiorPassButton.setText("Passed");
		passButtonClicked = true;
		interiorFailButton.setVisibility(View.GONE);
		if (interiorFailLayout.isShown()) {
			Utils.slide_up(this, interiorFailLayout);
			interiorFailLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * onClick handler - interior fail
	 */
	public void showInteriorFailSection(View v) {
		if (interiorFailLayout.isShown()) {
			Utils.slide_up(this, interiorFailLayout);
			interiorFailLayout.setVisibility(View.GONE);
		} else {
			interiorFailLayout.setVisibility(View.VISIBLE);
			Utils.slide_down(this, interiorFailLayout);
		}
	}

	/**
	 * onClick handler - lights pass
	 */
	public void passLights(View v) {
		lightsPassButton.setText("Passed");
		passButtonClicked = true;
		lightsFailButton.setVisibility(View.GONE);
		if (lightsFailLayout.isShown()) {
			Utils.slide_up(this, lightsFailLayout);
			lightsFailLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * onClick handler - lights fail
	 */
	public void showLightsFailSection(View v) {
		if (lightsFailLayout.isShown()) {
			Utils.slide_up(this, lightsFailLayout);
			lightsFailLayout.setVisibility(View.GONE);
		} else {
			lightsFailLayout.setVisibility(View.VISIBLE);
			Utils.slide_down(this, lightsFailLayout);
		}
	}

	/**
	 * onClick handler - locking pins pass
	 */
	public void passLockingPins(View v) {
		lockingPinsPassButton.setText("Passed");
		passButtonClicked = true;
		lockingPinsFailButton.setVisibility(View.GONE);
		if (lockingPinsFailLayout.isShown()) {
			Utils.slide_up(this, lockingPinsFailLayout);
			lockingPinsFailLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * onClick handler - locking pins fail
	 */
	public void showLockingPinsFailSection(View v) {
		if (lockingPinsFailLayout.isShown()) {
			Utils.slide_up(this, lockingPinsFailLayout);
			lockingPinsFailLayout.setVisibility(View.GONE);
		} else {
			lockingPinsFailLayout.setVisibility(View.VISIBLE);
			Utils.slide_down(this, lockingPinsFailLayout);
		}
	}

	/**
	 * onClick handler - tie downs pass
	 */
	public void passTieDowns(View v) {
		tieDownsPassButton.setText("Passed");
		passButtonClicked = true;
		tieDownsFailButton.setVisibility(View.GONE);
		if (tieDownsFailLayout.isShown()) {
			Utils.slide_up(this, tieDownsFailLayout);
			tieDownsFailLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * onClick handler - tie downs fail
	 */
	public void showTieDownsFailSection(View v) {
		if (tieDownsFailLayout.isShown()) {
			Utils.slide_up(this, tieDownsFailLayout);
			tieDownsFailLayout.setVisibility(View.GONE);
		} else {
			tieDownsFailLayout.setVisibility(View.VISIBLE);
			Utils.slide_down(this, tieDownsFailLayout);
		}
	}

	/**
	 * onClick handler - wheels pass
	 */
	public void passWheels(View v) {
		wheelsPassButton.setText("Passed");
		passButtonClicked = true;
		wheelsFailButton.setVisibility(View.GONE);
		if (wheelsFailLayout.isShown()) {
			Utils.slide_up(this, wheelsFailLayout);
			wheelsFailLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * onClick handler - wheels fail
	 */
	public void showWheelsFailSection(View v) {
		if (wheelsFailLayout.isShown()) {
			Utils.slide_up(this, wheelsFailLayout);
			wheelsFailLayout.setVisibility(View.GONE);
		} else {
			wheelsFailLayout.setVisibility(View.VISIBLE);
			Utils.slide_down(this, wheelsFailLayout);
		}
	}

	/**
	 * onClick handler - windshield pass
	 */
	public void passWindshield(View v) {
		windshieldPassButton.setText("Passed");
		passButtonClicked = true;
		windshieldFailButton.setVisibility(View.GONE);
		if (windshieldFailLayout.isShown()) {
			Utils.slide_up(this, windshieldFailLayout);
			windshieldFailLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * onClick handler - windshield fail
	 */
	public void showWindshieldFailSection(View v) {
		if (windshieldFailLayout.isShown()) {
			Utils.slide_up(this, windshieldFailLayout);
			windshieldFailLayout.setVisibility(View.GONE);
		} else {
			windshieldFailLayout.setVisibility(View.VISIBLE);
			Utils.slide_down(this, windshieldFailLayout);
		}
	}

	/**
	 * onClick handler - SAVE REPORT
	 */
	public void saveReport(View v) {
		addDvir();
	}

	private void addDvir() {
		Log.d(TAG, "addDvir Method started");

		dvir = new Dvir();

		// Initialize Firestore
		mFirestore = FirebaseFirestore.getInstance();
		mFirebaseAuth = FirebaseAuth.getInstance();
		// Create reference for new dvir, for use inside the transaction
		mDvirRef = mFirestore.collection("dvirs").document();

		//		dvir.setCreatedBy(mFirebaseAuth.getCurrentUser().getDisplayName());
		dvir.setCreatedBy(userName.getText().toString());
		dvir.setMobileName(mobileName.getText().toString());
		dvir.setMobileId(mobileId.getText().toString());
		dvir.setCreatedDate(Timestamp.now());

		if (dvir.getInspection() != null) {

			if (passButtonClicked) {
				dvir.getInspection().getBrakes().setPass(true);
			} else {
				dvir.getInspection().getBrakes().setPass(false);
				dvir.getInspection().getBrakes().setComments(brakesCommentsTv.getText().toString());
				// set image to be done later
			}

			if (passButtonClicked) {
				dvir.getInspection().getConnections().setPass(true);
			} else {
				dvir.getInspection().getConnections().setPass(false);
				dvir.getInspection().getConnections().setComments(brakesCommentsTv.getText().toString());
				// set image to be done later
			}
			if (passButtonClicked) {
				dvir.getInspection().getCouplings().setPass(true);
			} else {
				dvir.getInspection().getCouplings().setPass(false);
				dvir.getInspection().getCouplings().setComments(brakesCommentsTv.getText().toString());
				// set image to be done later
			}
			if (passButtonClicked) {
				dvir.getInspection().getEngine().setPass(true);
			} else {
				dvir.getInspection().getEngine().setPass(false);
				dvir.getInspection().getEngine().setComments(brakesCommentsTv.getText().toString());
				// set image to be done later
			}
			if (passButtonClicked) {
				dvir.getInspection().getExterior().setPass(true);
			} else {
				dvir.getInspection().getExterior().setPass(false);
				dvir.getInspection().getExterior().setComments(brakesCommentsTv.getText().toString());
				// set image to be done later
			}
			if (passButtonClicked) {
				dvir.getInspection().getInterior().setPass(true);
			} else {
				dvir.getInspection().getInterior().setPass(false);
				dvir.getInspection().getInterior().setComments(brakesCommentsTv.getText().toString());
				// set image to be done later
			}
			if (passButtonClicked) {
				dvir.getInspection().getLights().setPass(true);
			} else {
				dvir.getInspection().getLights().setPass(false);
				dvir.getInspection().getLights().setComments(brakesCommentsTv.getText().toString());
				// set image to be done later
			}
			if (passButtonClicked) {
				dvir.getInspection().getLockingPins().setPass(true);
			} else {
				dvir.getInspection().getLockingPins().setPass(false);
				dvir.getInspection().getLockingPins().setComments(brakesCommentsTv.getText().toString());
				// set image to be done later
			}
			if (passButtonClicked) {
				dvir.getInspection().getTieDowns().setPass(true);
			} else {
				dvir.getInspection().getTieDowns().setPass(false);
				dvir.getInspection().getTieDowns().setComments(brakesCommentsTv.getText().toString());
				// set image to be done later
			}
			if (passButtonClicked) {
				dvir.getInspection().getWheels().setPass(true);
			} else {
				dvir.getInspection().getWheels().setPass(false);
				dvir.getInspection().getWheels().setComments(brakesCommentsTv.getText().toString());
				// set image to be done later
			}
			if (passButtonClicked) {
				dvir.getInspection().getWindshield().setPass(true);
			} else {
				dvir.getInspection().getWindshield().setPass(false);
				dvir.getInspection().getWindshield().setComments(brakesCommentsTv.getText().toString());
				// set image to be done later
			}
		}

		mDvirRef.set(dvir).addOnCompleteListener(new OnCompleteListener<Void>() {
			@Override
			public void onComplete(@NonNull Task<Void> task) {

					if(task.isSuccessful()){
						View parentLayout = findViewById(android.R.id.content);
						Snackbar.make(parentLayout, "Inspection added!", Snackbar.LENGTH_SHORT).show();
					}else{
						View parentLayout = findViewById(android.R.id.content);
						Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
					}
				}
			});
		}
	}

