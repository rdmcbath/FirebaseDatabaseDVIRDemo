package com.mcbath.rebecca.firebasedatabasedvirtest1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.Dvir;

import java.text.SimpleDateFormat;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Rebecca McBath
 * on 2019-08-26.
 */
public class DvirDetailActivity extends AppCompatActivity implements View.OnClickListener {
	private static final String TAG = "DvirDetail";

	public static final String KEY_DVIR_ID = "key_dvir_id";

	private TextView mobileNameTextview, createdByTextview, createdDateTextview;
	private TextView brakesCommentsText, connectionsCommentsText, couplingsCommentsText, engineCommentsText, exteriorCommentsText, interiorCommentsText, lightsCommentsText;
	private TextView lockingpinsCommentsText, tiedownsCommentsText, wheelsCommentsText, windshieldCommentsText;
	private ImageView photoImage, topCardBackground, closePage, brakesPassImage, brakesFailImage, connectionsPassImage, connectionsFailImage;
	private ImageView brakesImageView, connectionsImageView, couplingsImageView, engineImageView, exteriorImageView, interiorImageView, lockingpinsImageView, tiedownsImageView, wheelsImageView, lightsImageView, windshieldImageView;
	private ImageView couplingsPassImage, couplingsFailImage, enginePassImage, engineFailImage, exteriorPassImage, exteriorFailImage;
	private ImageView interiorPassImage, interiorFailImage, lightsPassImage, lightsFailImage, lockingpinsPassImage, lockingpinsFailImage;
	private ImageView tiedownsPassImage, tiedownsFailImage, wheelsPassImage, wheelsFailImage, windshieldPassImage, windshieldFailImage;
	private boolean brakesPass, connectionsPass, couplingsPass, enginePass, exteriorPass, interiorPass, lightsPass;
	private boolean lockingpinsPass, tiedownsPass, wheelsPass, windshieldPass;
	private String brakesComments, connectionsComments, couplingsComments, engineComments, exteriorComments, mobileName, createdBy;
	private String interiorComments, lightsComments, lockingpinsComments, tiedownsComments, wheelsComments, windshieldComments;
	private String brakesPhoto, connectionsPhoto, couplingsPhoto, enginePhoto, exteriorPhoto, interiorPhoto, lightsPhoto, lockingpinsPhoto;
	private String tiedownsPhoto, wheelsPhoto, windshieldPhoto;
	private Timestamp createdDate;
	private LinearLayout emptyViewLayout, dvirDetailFailLayoutBrakes, dvirDetailFailLayoutConnections, dvirDetailFailLayoutCouplings, dvirDetailFailLayoutEngine, dvirDetailFailLayoutLights, inspectionDetailLayout;
	private LinearLayout dvirDetailFailLayoutExterior, dvirDetailFailLayoutInterior, dvirDetailFailLayoutLockingPins, dvirDetailFailLayoutTieDowns, dvirDetailFailLayoutWheels, dvirDetailFailLayoutWindshield;
	private FirebaseFirestore mFirestore;
	private DocumentReference mDvirRef;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dvir_detail);
		mobileNameTextview = findViewById(R.id.mobile_name_text);
		createdByTextview = findViewById(R.id.created_by_text);
		createdDateTextview = findViewById(R.id.created_date_text);
		closePage = findViewById(R.id.dvir_button_back);
		topCardBackground = findViewById(R.id.background_image);
		emptyViewLayout = findViewById(R.id.empty_view_layout);

		inspectionDetailLayout = findViewById(R.id.inspection_detail_layout);
		brakesFailImage = inspectionDetailLayout.findViewById(R.id.image_fail_brakes);
		brakesPassImage = inspectionDetailLayout.findViewById(R.id.image_pass_brakes);
		connectionsFailImage = inspectionDetailLayout.findViewById(R.id.image_fail_connections);
		connectionsPassImage = inspectionDetailLayout.findViewById(R.id.image_pass_connections);
		couplingsFailImage = inspectionDetailLayout.findViewById(R.id.image_fail_couplings);
		couplingsPassImage = inspectionDetailLayout.findViewById(R.id.image_pass_couplings);
		engineFailImage = inspectionDetailLayout.findViewById(R.id.image_fail_engine);
		enginePassImage = inspectionDetailLayout.findViewById(R.id.image_pass_engine);
		exteriorFailImage = inspectionDetailLayout.findViewById(R.id.image_fail_exterior);
		exteriorPassImage = inspectionDetailLayout.findViewById(R.id.image_pass_exterior);
		interiorFailImage = inspectionDetailLayout.findViewById(R.id.image_fail_interior);
		interiorPassImage = inspectionDetailLayout.findViewById(R.id.image_pass_interior);
		lightsFailImage = inspectionDetailLayout.findViewById(R.id.image_fail_lights);
		lightsPassImage = inspectionDetailLayout.findViewById(R.id.image_pass_lights);
		lockingpinsFailImage = inspectionDetailLayout.findViewById(R.id.image_fail_lockingpins);
		lockingpinsPassImage = inspectionDetailLayout.findViewById(R.id.image_pass_lockingpins);
		tiedownsFailImage = inspectionDetailLayout.findViewById(R.id.image_fail_tiedowns);
		tiedownsPassImage = inspectionDetailLayout.findViewById(R.id.image_pass_tiedowns);
		wheelsFailImage = inspectionDetailLayout.findViewById(R.id.image_fail_wheels);
		wheelsPassImage = inspectionDetailLayout.findViewById(R.id.image_pass_wheels);
		windshieldFailImage = inspectionDetailLayout.findViewById(R.id.image_fail_windshield);
		windshieldPassImage = inspectionDetailLayout.findViewById(R.id.image_pass_windshield);

		brakesCommentsText = inspectionDetailLayout.findViewById(R.id.comments_text_brakes);
		brakesImageView = inspectionDetailLayout.findViewById(R.id.photo_brakes);
		connectionsCommentsText = inspectionDetailLayout.findViewById(R.id.comments_text_connections);
		connectionsImageView = inspectionDetailLayout.findViewById(R.id.photo_connections);
		couplingsCommentsText = inspectionDetailLayout.findViewById(R.id.comments_text_couplings);
		couplingsImageView = inspectionDetailLayout.findViewById(R.id.photo_couplings);
		engineCommentsText = inspectionDetailLayout.findViewById(R.id.comments_text_engine);
		engineImageView = inspectionDetailLayout.findViewById(R.id.photo_engine);
		exteriorCommentsText = inspectionDetailLayout.findViewById(R.id.comments_text_exterior);
		exteriorImageView = inspectionDetailLayout.findViewById(R.id.photo_exterior);
		interiorCommentsText = inspectionDetailLayout.findViewById(R.id.comments_text_interior);
		interiorImageView = inspectionDetailLayout.findViewById(R.id.photo_interior);
		lightsCommentsText = inspectionDetailLayout.findViewById(R.id.comments_text_lights);
		lightsImageView = inspectionDetailLayout.findViewById(R.id.photo_lights);
		lockingpinsCommentsText = inspectionDetailLayout.findViewById(R.id.comments_text_lockingpins);
		lockingpinsImageView = inspectionDetailLayout.findViewById(R.id.photo_lockingpins);
		tiedownsCommentsText = inspectionDetailLayout.findViewById(R.id.comments_text_tiedowns);
		tiedownsImageView = inspectionDetailLayout.findViewById(R.id.photo_tiedowns);
		wheelsCommentsText = inspectionDetailLayout.findViewById(R.id.comments_text_wheels);
		wheelsImageView = inspectionDetailLayout.findViewById(R.id.photo_wheels);
		windshieldCommentsText = inspectionDetailLayout.findViewById(R.id.comments_text_windshield);
		windshieldImageView = inspectionDetailLayout.findViewById(R.id.photo_windshield);

		dvirDetailFailLayoutBrakes = inspectionDetailLayout.findViewById(R.id.fail_layout_brakes);
		dvirDetailFailLayoutConnections = inspectionDetailLayout.findViewById(R.id.fail_layout_connections);
		dvirDetailFailLayoutCouplings = inspectionDetailLayout.findViewById(R.id.fail_layout_couplings);
		dvirDetailFailLayoutEngine = inspectionDetailLayout.findViewById(R.id.fail_layout_engine);
		dvirDetailFailLayoutExterior = inspectionDetailLayout.findViewById(R.id.fail_layout_exterior);
		dvirDetailFailLayoutInterior = inspectionDetailLayout.findViewById(R.id.fail_layout_interior);
		dvirDetailFailLayoutLights = inspectionDetailLayout.findViewById(R.id.fail_layout_lights);
		dvirDetailFailLayoutLockingPins = inspectionDetailLayout.findViewById(R.id.fail_layout_lockingpins);
		dvirDetailFailLayoutTieDowns = inspectionDetailLayout.findViewById(R.id.fail_layout_tiedowns);
		dvirDetailFailLayoutWheels = inspectionDetailLayout.findViewById(R.id.fail_layout_wheels);
		dvirDetailFailLayoutWindshield = inspectionDetailLayout.findViewById(R.id.fail_layout_windshield);

		// Get DVIR ID from extras
		String dvirId = getIntent().getExtras().getString(KEY_DVIR_ID);
		if (dvirId == null) {
			throw new IllegalArgumentException("Must pass extra " + KEY_DVIR_ID);
		}

		// Initialize Firestore
		mFirestore = FirebaseFirestore.getInstance();
		// Get reference to the dvir
		mDvirRef = mFirestore.collection("dvirs").document(dvirId);
		// Navigate back to main activity
		closePage.setOnClickListener(this);

		fetchSingleDvir();
	}

	public void fetchSingleDvir() {
		mDvirRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
			@Override
			public void onComplete(@NonNull Task<DocumentSnapshot> task) {
				if (task.isSuccessful()) {
					// get the data from the snapshot
					DocumentSnapshot doc = task.getResult();

					if (doc != null) {

						mobileName = (String) doc.get("mobileName");
						createdBy = (String) doc.get("createdBy");
						createdDate = (Timestamp) doc.get("createdDate");

						// map the snapshot data back to custom Pojo object Dvir
						Dvir dvir = doc.toObject(Dvir.class);

						if (dvir != null) {
							brakesPass = dvir.getInspection().getBrakes().getPass();
							if (!brakesPass) {
								brakesComments = dvir.getInspection().getBrakes().getComments();
								brakesPhoto = dvir.getInspection().getBrakes().getPhotoUrl();
							}

							connectionsPass = dvir.getInspection().getConnections().getPass();
							if (!connectionsPass) {
								connectionsComments = dvir.getInspection().getConnections().getComments();
								connectionsPhoto = dvir.getInspection().getConnections().getPhotoUrl();
							}

							couplingsPass = dvir.getInspection().getCouplings().getPass();
							if (!couplingsPass) {
								couplingsComments = dvir.getInspection().getCouplings().getComments();
								couplingsPhoto = dvir.getInspection().getCouplings().getPhotoUrl();
							}

							enginePass = dvir.getInspection().getEngine().getPass();
							if (!enginePass) {
								engineComments = dvir.getInspection().getEngine().getComments();
								enginePhoto = dvir.getInspection().getEngine().getPhotoUrl();
							}

							exteriorPass = dvir.getInspection().getExterior().getPass();
							if (!exteriorPass) {
								exteriorComments = dvir.getInspection().getExterior().getComments();
								exteriorPhoto = dvir.getInspection().getExterior().getPhotoUrl();
							}

							interiorPass = dvir.getInspection().getInterior().getPass();
							if (!interiorPass) {
								interiorComments = dvir.getInspection().getInterior().getComments();
								interiorPhoto = dvir.getInspection().getInterior().getPhotoUrl();
							}

							lightsPass = dvir.getInspection().getLights().getPass();
							if (!lightsPass) {
								lightsComments = dvir.getInspection().getLights().getComments();
								lightsPhoto = dvir.getInspection().getLights().getPhotoUrl();
							}

							lockingpinsPass = dvir.getInspection().getLockingPins().getPass();
							if (!lockingpinsPass) {
								lockingpinsComments = dvir.getInspection().getLockingPins().getComments();
								lockingpinsPhoto = dvir.getInspection().getLockingPins().getPhotoUrl();
							}

							tiedownsPass = dvir.getInspection().getTieDowns().getPass();
							if (!tiedownsPass) {
								tiedownsComments = dvir.getInspection().getTieDowns().getComments();
								tiedownsPhoto = dvir.getInspection().getTieDowns().getPhotoUrl();
							}

							wheelsPass = dvir.getInspection().getWheels().getPass();
							if (!wheelsPass) {
								wheelsComments = dvir.getInspection().getWheels().getComments();
								wheelsPhoto = dvir.getInspection().getWheels().getPhotoUrl();
							}

							windshieldPass = dvir.getInspection().getWindshield().getPass();
							if (!windshieldPass) {
								windshieldComments = dvir.getInspection().getWindshield().getComments();
								windshieldPhoto = dvir.getInspection().getWindshield().getPhotoUrl();
							}

							displayDvir();

						} else {
							emptyViewLayout.setVisibility(View.VISIBLE);
						}
					}
				}
			}
		})
				.addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
					}
				});
	}

	private void displayDvir() {

		mobileNameTextview.setText(mobileName);
		createdByTextview.setText(createdBy);
		java.util.Date dateFromServer = createdDate.toDate();
		String formatter = new SimpleDateFormat("M-dd-yyyy, h:mm a", Locale.getDefault()).format(dateFromServer);
		createdDateTextview.setText(formatter);


		// get a reference to the detail layout
//		LinearLayout dvirDetailLayout = findViewById(R.id.dvirDetailLayout);
		// inflate included layout
//		View dvirFailLayout = LayoutInflater.from(this).inflate(R.layout.dvir_fail_layout,null);

//		dvirFailLayout = dvirDetailFailLayout.findViewById(R.id.dvir_fail_layout);
//		photoImage = dvirDetailFailLayout.findViewById(R.id.photo);
//		commentsText = dvirDetailFailLayout.findViewById(R.id.comments_text);

		// Brakes
		if (brakesPass) {
			brakesPassImage.setVisibility(View.VISIBLE);
			brakesFailImage.setVisibility(View.GONE);
			dvirDetailFailLayoutBrakes.setVisibility(View.GONE);
		} else {
			dvirDetailFailLayoutBrakes.setVisibility(View.VISIBLE);
			brakesPassImage.setVisibility(View.GONE);
			brakesFailImage.setVisibility(View.VISIBLE);
			brakesCommentsText.setText(brakesComments);
			// show photo
			Glide.with(this)
					.load(brakesPhoto)
					.into(brakesImageView);
		}

		// Connections
		if (connectionsPass) {
			connectionsPassImage.setVisibility(View.VISIBLE);
			connectionsFailImage.setVisibility(View.GONE);
			dvirDetailFailLayoutConnections.setVisibility(View.GONE);
		} else {
			dvirDetailFailLayoutConnections.setVisibility(View.VISIBLE);
			connectionsPassImage.setVisibility(View.GONE);
			connectionsFailImage.setVisibility(View.VISIBLE);
			connectionsCommentsText.setText(connectionsComments);
			// show photo
			Glide.with(this)
					.load(connectionsPhoto)
					.into(connectionsImageView);
		}

		// Couplings
		if (couplingsPass) {
			couplingsPassImage.setVisibility(View.VISIBLE);
			couplingsFailImage.setVisibility(View.GONE);
			dvirDetailFailLayoutCouplings.setVisibility(View.GONE);
		} else {
			dvirDetailFailLayoutCouplings.setVisibility(View.VISIBLE);
			couplingsPassImage.setVisibility(View.GONE);
			couplingsFailImage.setVisibility(View.VISIBLE);
			couplingsCommentsText.setText(couplingsComments);
			// show photo
			Glide.with(this)
					.load(couplingsPhoto)
					.into(couplingsImageView);
		}

		// Engine
		if (enginePass) {
			enginePassImage.setVisibility(View.VISIBLE);
			engineFailImage.setVisibility(View.GONE);
			dvirDetailFailLayoutEngine.setVisibility(View.GONE);
		} else {
			dvirDetailFailLayoutEngine.setVisibility(View.VISIBLE);
			enginePassImage.setVisibility(View.GONE);
			engineFailImage.setVisibility(View.VISIBLE);
			engineCommentsText.setText(engineComments);
			// show photo
			Glide.with(this)
					.load(enginePhoto)
					.into(engineImageView);
		}

		// Exterior
		if (exteriorPass) {
			exteriorPassImage.setVisibility(View.VISIBLE);
			exteriorFailImage.setVisibility(View.GONE);
			dvirDetailFailLayoutExterior.setVisibility(View.GONE);
		} else {
			dvirDetailFailLayoutExterior.setVisibility(View.VISIBLE);
			exteriorPassImage.setVisibility(View.GONE);
			exteriorFailImage.setVisibility(View.VISIBLE);
			exteriorCommentsText.setText(exteriorComments);
			// show photo
			Glide.with(this)
					.load(exteriorPhoto)
					.into(exteriorImageView);
		}

		// Interior
		if (interiorPass) {
			interiorPassImage.setVisibility(View.VISIBLE);
			interiorFailImage.setVisibility(View.GONE);
			dvirDetailFailLayoutInterior.setVisibility(View.GONE);
		} else {
			dvirDetailFailLayoutInterior.setVisibility(View.VISIBLE);
			interiorPassImage.setVisibility(View.GONE);
			interiorFailImage.setVisibility(View.VISIBLE);
			interiorCommentsText.setText(interiorComments);
			// show photo
			Glide.with(this)
					.load(interiorPhoto)
					.into(interiorImageView);
		}

		// Lights
		if (lightsPass) {
			lightsPassImage.setVisibility(View.VISIBLE);
			lightsFailImage.setVisibility(View.GONE);
			dvirDetailFailLayoutLights.setVisibility(View.GONE);
		} else {
			dvirDetailFailLayoutLights.setVisibility(View.VISIBLE);
			lightsPassImage.setVisibility(View.GONE);
			lightsFailImage.setVisibility(View.VISIBLE);
			lightsCommentsText.setText(lightsComments);
			// show photo
			Glide.with(this)
					.load(lightsPhoto)
					.into(lightsImageView);
		}

		// Locking Pins
		if (lockingpinsPass) {
			lockingpinsPassImage.setVisibility(View.VISIBLE);
			lockingpinsFailImage.setVisibility(View.GONE);
			dvirDetailFailLayoutLockingPins.setVisibility(View.GONE);
		} else {
			dvirDetailFailLayoutLockingPins.setVisibility(View.VISIBLE);
			lockingpinsPassImage.setVisibility(View.GONE);
			lockingpinsFailImage.setVisibility(View.VISIBLE);
			lockingpinsCommentsText.setText(lockingpinsComments);
			// show photo
			Glide.with(this)
					.load(lockingpinsPhoto)
					.into(lockingpinsImageView);
		}

		// Tie Downs
		if (tiedownsPass) {
			tiedownsPassImage.setVisibility(View.VISIBLE);
			tiedownsFailImage.setVisibility(View.GONE);
			dvirDetailFailLayoutTieDowns.setVisibility(View.GONE);
		} else {
			dvirDetailFailLayoutTieDowns.setVisibility(View.VISIBLE);
			tiedownsPassImage.setVisibility(View.GONE);
			tiedownsFailImage.setVisibility(View.VISIBLE);
			tiedownsCommentsText.setText(tiedownsComments);
			// show photo
			Glide.with(this)
					.load(tiedownsPhoto)
					.into(tiedownsImageView);
		}

		// Wheels
		if (wheelsPass) {
			wheelsPassImage.setVisibility(View.VISIBLE);
			wheelsFailImage.setVisibility(View.GONE);
			dvirDetailFailLayoutWheels.setVisibility(View.GONE);
		} else {
			dvirDetailFailLayoutWheels.setVisibility(View.VISIBLE);
			windshieldPassImage.setVisibility(View.GONE);
			wheelsFailImage.setVisibility(View.VISIBLE);
			wheelsCommentsText.setText(wheelsComments);
			// show photo
			Glide.with(this)
					.load(wheelsPhoto)
					.into(wheelsImageView);
		}

		// Windshield
		if (windshieldPass) {
			windshieldPassImage.setVisibility(View.VISIBLE);
			windshieldFailImage.setVisibility(View.GONE);
			dvirDetailFailLayoutWindshield.setVisibility(View.GONE);
		} else {
			dvirDetailFailLayoutWindshield.setVisibility(View.VISIBLE);
			windshieldPassImage.setVisibility(View.GONE);
			windshieldFailImage.setVisibility(View.VISIBLE);
			windshieldCommentsText.setText(windshieldComments);
			// show photo
			Glide.with(this)
					.load(windshieldPhoto)
					.into(windshieldImageView);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.dvir_button_back:
				onBackPressed();
				break;
		}
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}
}

