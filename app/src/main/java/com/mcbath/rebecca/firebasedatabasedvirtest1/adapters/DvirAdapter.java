package com.mcbath.rebecca.firebasedatabasedvirtest1.adapters;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.mcbath.rebecca.firebasedatabasedvirtest1.R;
import com.mcbath.rebecca.firebasedatabasedvirtest1.models.Dvir;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior.ESTIMATE;

/**
 * Created by Rebecca McBath
 * on 2019-08-27.
 */
public class DvirAdapter extends FirestoreAdapter<DvirAdapter.ViewHolder> {

	public interface OnDvirSelectedListener {
		void onDvirSelected(DocumentSnapshot dvir);
	}

	private OnDvirSelectedListener mListener;

	public DvirAdapter(Query query, OnDvirSelectedListener listener) {
		super(query);
		mListener = listener;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_dvir, parent, false));
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.bind(getSnapshot(position), mListener);
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		ImageView dvirItemImageView;
		ImageView dvirItemArrow;
		TextView dateTextView;
		TextView mobileTextView;

		public ViewHolder(View itemView) {
			super(itemView);
			dateTextView = itemView.findViewById(R.id.dvir_item_date);
			mobileTextView = itemView.findViewById(R.id.dvir_item_mobile);
			dvirItemImageView = itemView.findViewById(R.id.dvir_item_image);
			dvirItemArrow = itemView.findViewById(R.id.item_arrow);
		}

		public void bind(final DocumentSnapshot snapshot,
		                 final OnDvirSelectedListener listener) {

			Dvir dvir = snapshot.toObject(Dvir.class);

			dvirItemImageView.setImageResource(R.drawable.ic_assignment_black_24dp);
			dvirItemArrow.setImageResource(R.drawable.ic_keyboard_arrow_right_black_24dp);

			Date dateFromServer = null;
			if (dvir.getCreatedDate() != null) {
				dateFromServer = dvir.getCreatedDate().toDate();
				String formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault()).format(dateFromServer);
				dateTextView.setText(formatter);
			}
				mobileTextView.setText(dvir.getMobileName());

				// Click listener
				itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (listener != null) {
							listener.onDvirSelected(snapshot);
						}
					}
				});
			}
		}

		public void deleteItem(int position) {
			getSnapshot(position).getReference().delete();
		}
	}