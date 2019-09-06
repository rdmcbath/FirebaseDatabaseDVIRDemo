package com.mcbath.rebecca.firebasedatabasedvirtest1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.Brakes;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.Connections;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.Couplings;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.Dvir;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.Engine;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.Exterior;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.Inspection;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.Interior;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.Lights;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.LockingPins;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.TieDowns;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.Wheels;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.Windshield;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

/**
 * Created by Rebecca McBath
 * on 2019-08-30.
 */
public class AddDvirActivity extends AppCompatActivity {
	private static final String TAG = "AddDvirActivity";

	private final String COLLECTION_KEY = "dvirs";
	private static final int CAMERA_PERMISSION_REQUEST_CODE = 321;
	static final int REQUEST_TAKE_PHOTO = 1;
	static final int REQUEST_GALLERY_PHOTO = 2;
	File mPhotoFile;
	FileCompressor mCompressor;
	private LinearLayout brakesFailLayout, connectionsFailLayout, couplingsFailLayout, engineFailLayout, exteriorFailLayout, interiorFailLayout, lightsFailLayout, lockingPinsFailLayout, tieDownsFailLayout, wheelsFailLayout, windshieldFailLayout;
	private EditText brakesCommentsTv, connectionsCommentsTv, couplingsCommentsTv, engineCommentsTv, exteriorCommentsTv, interiorCommentsTv, lightsCommentsTv, lockingPinsCommentsTv, tiedownsCommentsTv, wheelsCommentsTv, windshieldCommentsTv;
	private EditText mobileName, mobileId;
	private Button brakesPassButton, brakesFailButton, connectionsPassButton, connectionsFailButton, couplingsPassButton, couplingsFailButton, enginePassButton, engineFailButton, exteriorPassButton, exteriorFailButton, lightsPassButton, lightsFailButton;
	private Button interiorPassButton, interiorFailButton, lockingPinsPassButton, lockingPinsFailButton, tieDownsPassButton, tieDownsFailButton, wheelsPassButton, wheelsFailButton, windshieldPassButton, windshieldFailButton;
	private ImageView brakesFailIv, connectionsFailIv, couplingsFailIv, engineFailIv, exteriorFailIv, interiorFailIv, lightsFailIv, lockingPinsFailIv, tieDownsFailIv, wheelsFailIv, windshieldFailIv;
	private FrameLayout brakesAddPhotolayout, connectionsAddPhotolayout, couplingsAddPhotolayout, engineAddPhotolayout, exteriorAddPhotolayout, interiorAddPhotolayout, lightsAddPhotolayout, lockingPinsAddPhotolayout;
	private FrameLayout tieDownsAddPhotolayout, wheelsAddPhotolayout, windshieldAddPhotolayout;
	private Dvir dvir;
	private Inspection inspection;
	private FirebaseFirestore mFirestore;
	private DocumentReference mDvirRef;
	private FirebaseAuth mFirebaseAuth;
	private FirebaseStorage mFirebaseStorage;
	private StorageReference mPhotosStorageReference;
	private boolean passBrakes = false;
	private boolean passConnections = false;
	private boolean passCouplings = false;
	private boolean passEngine = false;
	private boolean passExterior = false;
	private boolean passInterior = false;
	private boolean passLights = false;
	private boolean passLockingPins = false;
	private boolean passTieDowns = false;
	private boolean passWheels = false;
	private boolean passWindshield = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_dvir);

		Toolbar mToolbar = findViewById(R.id.toolbar);
		initializeViews();
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		mFirebaseStorage = FirebaseStorage.getInstance();
		mPhotosStorageReference = mFirebaseStorage.getReference().child("dvir_photos");
		mFirestore = FirebaseFirestore.getInstance();

		mCompressor = new FileCompressor(this);

		initializePhotoButtons();
	}

	public void initializePhotoButtons() {

		brakesAddPhotolayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				selectImage();
			}
		});
		connectionsAddPhotolayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				selectImage();
			}
		});
		couplingsAddPhotolayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				selectImage();
			}
		});
		engineAddPhotolayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				selectImage();
			}
		});
		exteriorAddPhotolayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				selectImage();
			}
		});
		interiorAddPhotolayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				selectImage();
			}
		});
		lightsAddPhotolayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				selectImage();
			}
		});
		lockingPinsAddPhotolayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				selectImage();
			}
		});
		tieDownsAddPhotolayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				selectImage();
			}
		});
		wheelsAddPhotolayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				selectImage();
			}
		});
		windshieldAddPhotolayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				selectImage();
			}
		});
	}

	/**
	 * Alert dialog for capture or select from galley
	 */
	private void selectImage() {
		final CharSequence[] items = {
				"Take Photo", "Choose from Gallery", "Cancel"
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(AddDvirActivity.this);
		builder.setItems(items, (dialog, item) -> {
			if (items[item].equals("Take Photo")) {
				requestStoragePermission(true);
			} else if (items[item].equals("Choose from Gallery")) {
				requestStoragePermission(false);
			} else if (items[item].equals("Cancel")) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

	/**
	 * Requesting multiple permissions (storage and camera) at once
	 * This uses multiple permission model from dexter library
	 */
	private void requestStoragePermission(boolean isCamera) {
		Dexter.withActivity(this)
				.withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
				.withListener(new MultiplePermissionsListener() {
					@Override
					public void onPermissionsChecked(MultiplePermissionsReport report) {
						// check if all permissions are granted
						if (report.areAllPermissionsGranted()) {
							if (isCamera) {
								dispatchTakePictureIntent();
							} else {
								dispatchGalleryIntent();
							}
						}
						// check for permanent denial of any permission
						if (report.isAnyPermissionPermanentlyDenied()) {
							// permission denied
						}
					}

					@Override
					public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
					                                               PermissionToken token) {
						token.continuePermissionRequest();
					}
				})
				.withErrorListener(
						error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT)
								.show())
				.onSameThread()
				.check();
	}

	/**
	 * Capture image from camera
	 */
	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				ex.printStackTrace();
				// Error occurred while creating the File
			}
			if (photoFile != null) {
				Uri photoURI = FileProvider.getUriForFile(this,
						BuildConfig.APPLICATION_ID + ".provider",
						photoFile);

				mPhotoFile = photoFile;
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
				startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
			}
		}
	}

	/**
	 * Select image from gallery
	 */
	private void dispatchGalleryIntent() {
		Intent pickPhoto = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_TAKE_PHOTO) {
				try {
					mPhotoFile = mCompressor.compressToFile(mPhotoFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				Glide.with(AddDvirActivity.this)
						.load(mPhotoFile)
						.apply(new RequestOptions().centerCrop()
								.placeholder(R.drawable.broken_seat_belt))
						.into(brakesFailIv);

				brakesAddPhotolayout.setVisibility(View.GONE);
				brakesFailIv.setVisibility(View.VISIBLE);

			} else if (requestCode == REQUEST_GALLERY_PHOTO) {
				Uri selectedImage = data.getData();
				try {
					mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
				} catch (IOException e) {
					e.printStackTrace();
				}
				Glide.with(AddDvirActivity.this)
						.load(mPhotoFile)
						.apply(new RequestOptions().centerCrop()
								.placeholder(R.drawable.broken_seat_belt))
						.into(brakesFailIv);

				brakesAddPhotolayout.setVisibility(View.GONE);
				brakesFailIv.setVisibility(View.VISIBLE);
			}
		}
	}

	/**
	 * Create file with current timestamp name
	 *
	 * @throws IOException
	 */
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
		String mFileName = "JPEG_" + timeStamp + "_";
		File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
		return mFile;
	}

	public String getRealPathFromUri(Uri contentUri) {
		String[] proj = {MediaStore.Audio.Media.DATA};
		Cursor cursor = managedQuery(contentUri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	/**
	 * onClick handler - brakes pass
	 */
	public void passBrakes(View v) {
		brakesPassButton.setText("Passed");
		passBrakes = true;
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
		passConnections = true;
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
		passCouplings = true;
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
		passEngine = true;
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
		passExterior = true;
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
		passInterior = true;
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
		passLights = true;
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
		passLockingPins = true;
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
		passTieDowns = true;
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
		passWheels = true;
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
		passWindshield = true;
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
	 * upload photo assets to Firebase Storage
	 */
	public void saveReport(View v) {
		addDvir();
		//		uploadImageAssets();
	}

	private void addDvir() {
		Log.d(TAG, "addDvir Method started");

		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

		dvir = new Dvir();
		dvir.setCreatedBy(user.getDisplayName());
		dvir.setMobileName(mobileName.getText().toString());
		dvir.setMobileId(mobileId.getText().toString());
		dvir.setCreatedDate(Timestamp.now());

		Brakes brakes = new Brakes();
		Connections connections = new Connections();
		Couplings couplings = new Couplings();
		Engine engine = new Engine();
		Exterior exterior = new Exterior();
		Interior interior = new Interior();
		Lights lights = new Lights();
		LockingPins lockingPins = new LockingPins();
		TieDowns tieDowns = new TieDowns();
		Wheels wheels = new Wheels();
		Windshield windshield = new Windshield();
		inspection = new Inspection(brakes, lights, wheels, connections, exterior, couplings, tieDowns, lockingPins, interior, windshield, engine);

		if (passBrakes) {
			inspection.getBrakes().setPass(true);
		} else {
			inspection.getBrakes().setPass(false);
			inspection.getBrakes().setComments(brakesCommentsTv.getText().toString());
			// can't set image url string because we haven't saved to storage yet
			//			inspection.getBrakes().setPhotoUrl(brakesDownloadUrl.toString());
		}

		if (passConnections) {
			inspection.getConnections().setPass(true);
		} else {
			inspection.getConnections().setPass(false);
			inspection.getConnections().setComments(connectionsCommentsTv.getText().toString());
			// set image to be done later
		}
		if (passCouplings) {
			inspection.getCouplings().setPass(true);
		} else {
			inspection.getCouplings().setPass(false);
			inspection.getCouplings().setComments(couplingsCommentsTv.getText().toString());
			// set image to be done later
		}
		if (passEngine) {
			inspection.getEngine().setPass(true);
		} else {
			inspection.getEngine().setPass(false);
			inspection.getEngine().setComments(engineCommentsTv.getText().toString());
			// set image to be done later
		}
		if (passExterior) {
			inspection.getExterior().setPass(true);
		} else {
			inspection.getExterior().setPass(false);
			inspection.getExterior().setComments(exteriorCommentsTv.getText().toString());
			// set image to be done later
		}
		if (passInterior) {
			inspection.getInterior().setPass(true);
		} else {
			inspection.getInterior().setPass(false);
			inspection.getInterior().setComments(interiorCommentsTv.getText().toString());
			// set image to be done later
		}
		if (passLights) {
			inspection.getLights().setPass(true);
		} else {
			inspection.getLights().setPass(false);
			inspection.getLights().setComments(lightsCommentsTv.getText().toString());
			// set image to be done later
		}
		if (passLockingPins) {
			inspection.getLockingPins().setPass(true);
		} else {
			inspection.getLockingPins().setPass(false);
			inspection.getLockingPins().setComments(lockingPinsCommentsTv.getText().toString());
			// set image to be done later
		}
		if (passTieDowns) {
			inspection.getTieDowns().setPass(true);
		} else {
			inspection.getTieDowns().setPass(false);
			inspection.getTieDowns().setComments(tiedownsCommentsTv.getText().toString());
			// set image to be done later
		}
		if (passWheels) {
			inspection.getWheels().setPass(true);
		} else {
			inspection.getWheels().setPass(false);
			inspection.getWheels().setComments(wheelsCommentsTv.getText().toString());
			// set image to be done later
		}
		if (passWindshield) {
			inspection.getWindshield().setPass(true);
		} else {
			inspection.getWindshield().setPass(false);
			inspection.getWindshield().setComments(windshieldCommentsTv.getText().toString());
			// set image to be done later
		}

		dvir.setInspection(inspection);

		mFirestore.collection(COLLECTION_KEY)
				.add(dvir)
				.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
					@Override
					public void onSuccess(DocumentReference documentReference) {
						Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
						View parentLayout = findViewById(android.R.id.content);
						Snackbar.make(parentLayout, "Inspection added: Id = " + documentReference.getId(), Snackbar.LENGTH_SHORT).show();
					}
				})
				.addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						Log.w(TAG, "Error adding document", e);
					}
				});
	}

	public void uploadImageAssets() {

		// Get a reference to store file at dvir_photos/<FILENAME>
		final Uri[] mPhotoUrl = {Uri.parse(String.valueOf(mPhotoFile))};
		StorageReference photoRef = mPhotosStorageReference.child(mPhotoUrl[0].getLastPathSegment());

		// Upload file to Firebase Storage
		photoRef.putFile(mPhotoUrl[0])
				.addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
					public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
						Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
						while (!urlTask.isSuccessful()) ;
						mPhotoUrl[0] = urlTask.getResult();
					}
				});
	}

	private void initializeViews() {
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
		mobileName = findViewById(R.id.mobileName_editText);
		mobileId = findViewById(R.id.mobileId_editText);
		brakesAddPhotolayout = findViewById(R.id.brakesAddPhotolayout);
		connectionsAddPhotolayout = findViewById(R.id.connectionsAddPhotolayout);
		couplingsAddPhotolayout = findViewById(R.id.couplingsAddPhotolayout);
		engineAddPhotolayout = findViewById(R.id.engineAddPhotolayout);
		exteriorAddPhotolayout = findViewById(R.id.exteriorAddPhotolayout);
		interiorAddPhotolayout = findViewById(R.id.interiorAddPhotolayout);
		lockingPinsAddPhotolayout = findViewById(R.id.lockingPinsAddPhotolayout);
		tieDownsAddPhotolayout = findViewById(R.id.tieDownsAddPhotolayout);
		wheelsAddPhotolayout = findViewById(R.id.wheelsAddPhotolayout);
		windshieldAddPhotolayout = findViewById(R.id.windshieldAddPhotolayout);
		lightsAddPhotolayout = findViewById(R.id.lightsAddPhotolayout);
	}
}

