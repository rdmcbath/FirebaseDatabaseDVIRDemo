package com.mcbath.rebecca.firebasedatabasedvirtest1;

import androidx.annotation.NonNull;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.mcbath.rebecca.firebasedatabasedvirtest1.adapters.DvirAdapter;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.Dvir;
import com.mcbath.rebecca.firebasedatabasedvirtest1.viewmodel.MainActivityViewModel;
import java.util.Collections;
import java.util.List;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Rebecca McBath
 * on 2019-08-26.
 */
public class MainActivity extends AppCompatActivity implements DvirAdapter.OnDvirSelectedListener {
	private static final String TAG = "MainActivity";

	private static final int RC_SIGN_IN = 9001;

	private final String COLLECTION_KEY = "dvirs";
	private static final int LIMIT = 100;
	private MainActivityViewModel mViewModel;
	private Toolbar mToolbar;
	private RecyclerView mDvirRecyclerView;
	private ViewGroup mEmptyView;
	private DvirAdapter mDvirAdapter;
	private FirebaseFirestore mFirestore;
	private ProgressBar mProgressBar;
	private FloatingActionButton mFab;
	private Query mQuery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mToolbar = findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);

		mDvirRecyclerView = findViewById(R.id.recycler_dvirs);
		mProgressBar = findViewById(R.id.progress_loading);
		mFab = findViewById(R.id.fab);
		mEmptyView = findViewById(R.id.view_empty);

		mProgressBar.setVisibility(ProgressBar.VISIBLE);

		// View model
		mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

		// Enable Firestore logging
		FirebaseFirestore.setLoggingEnabled(true);
		// Initialize Firestore and the main ListView
		mFirestore = FirebaseFirestore.getInstance();
		showDvirList();

		mProgressBar.setVisibility(ProgressBar.INVISIBLE);

		mFab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// add a new DVIR
				Intent intent = new Intent(MainActivity.this, AddDvirActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
			}
		});
	}

	private void showDvirList() {

		// Get ${LIMIT} DVIRs
		mQuery = mFirestore.collection(COLLECTION_KEY)
				.orderBy("createdDate", Query.Direction.DESCENDING)
				.limit(LIMIT);

		Log.d(TAG, "showDVIRList: mQuery = " + mQuery);

		// RecyclerView
		mDvirAdapter = new DvirAdapter(mQuery, this) {

			@Override
			protected void onDataChanged() {
				// Show/hide content if the query returns empty.
				if (getItemCount() == 0) {
					mDvirRecyclerView.setVisibility(View.GONE);
					mEmptyView.setVisibility(View.VISIBLE);
				} else {
					mDvirRecyclerView.setVisibility(View.VISIBLE);
					mEmptyView.setVisibility(View.GONE);
				}
			}

			@Override
			protected void onError(FirebaseFirestoreException e) {
				// Show a snackbar on errors
				Snackbar.make(findViewById(android.R.id.content),
						"Error: check logs for info.", Snackbar.LENGTH_LONG).show();
			}
		};

		mDvirRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mDvirRecyclerView.setAdapter(mDvirAdapter);

		new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
			@Override
			public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
				return false;
			}

			// Called when a user swipes left or right on a ViewHolder
			@Override
			public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int swipeDir) {
				// Here is where you'll implement swipe to delete
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setMessage("Delete this inspection?");
				builder.setCancelable(true);
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Get the position of the item to be deleted
						int position = viewHolder.getAdapterPosition();
						mDvirAdapter.deleteItem(position);
					}
				});

				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User cancelled the dialog, so let's refresh the adapter to prevent hiding the item from UI
						mDvirAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
						dialog.cancel();
					}
				})
						.create()
						.show();
			}
		}).attachToRecyclerView(mDvirRecyclerView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.sign_out_menu:
				AuthUI.getInstance().signOut(this);
				startSignIn();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RC_SIGN_IN) {
			IdpResponse response = IdpResponse.fromResultIntent(data);
			mViewModel.setIsSigningIn(false);

			if (resultCode != RESULT_OK) {
				if (response == null) {
					// User pressed the back button.
					finish();
				} else if (response.getError() != null
						&& response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
					showSignInErrorDialog(R.string.message_no_network);
				} else {
					showSignInErrorDialog(R.string.message_unknown);
				}
			}
		}
	}

	private boolean shouldStartSignIn() {
		return (!mViewModel.getIsSigningIn() && FirebaseAuth.getInstance().getCurrentUser() == null);
	}

	private void startSignIn() {
		// Sign in with FirebaseUI
		Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
				.setAvailableProviders(Collections.singletonList(
						new AuthUI.IdpConfig.EmailBuilder().build()))
				.setIsSmartLockEnabled(false)
				.build();

		startActivityForResult(intent, RC_SIGN_IN);
		mViewModel.setIsSigningIn(true);
	}

	private void showSignInErrorDialog(@StringRes int message) {
		AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle(R.string.title_sign_in_error)
				.setMessage(message)
				.setCancelable(false)
				.setPositiveButton(R.string.option_retry, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						startSignIn();
					}
				})
				.setNegativeButton(R.string.option_exit, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						finish();
					}
				}).create();

		dialog.show();
	}

	@Override
	public void onStart() {
		super.onStart();

		// Start sign in if necessary
		if (shouldStartSignIn()) {
			startSignIn();
			return;
		}

		// Start listening for Firestore updates
		if (mDvirAdapter != null) {
			mDvirAdapter.startListening();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mDvirAdapter != null) {
			mDvirAdapter.stopListening();
		}
	}

	@Override
	public void onDvirSelected(DocumentSnapshot dvir) {
		Intent intent = new Intent(this, DvirDetailActivity.class);
		intent.putExtra(DvirDetailActivity.KEY_DVIR_ID, dvir.getId());
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
}

