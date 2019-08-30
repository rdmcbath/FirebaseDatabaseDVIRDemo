package com.mcbath.rebecca.firebasedatabasedvirtest1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Rebecca McBath
 * on 2019-08-30.
 */
public class AddDvirActivity extends AppCompatActivity {
	private static final String TAG = "AddDvirActivity";

	private final String COLLECTION_KEY = "dvirs";

	private LinearLayout brakesFailLayout, connectionsFailLayout, couplingsFailLayout, engineFailLayout, exteriorFailLayout, interiorFailLayout, lightsFailLayout, lockingPinsFailLayout, tieDownsFailLayout, wheelsFailLayout, windshieldFailLayout;
	private TextView brakesCommentsTv, connectionsCommentsTv, couplingsCommentsTv, engineCommentsTv, exteriorCommentsTv, interiorCommentsTv, lightsCommentsTv, lockingPinsCommentsTv, tiedownsCommentsTv, wheelsCommentsTv, windshieldCommentsTv;
	private Button brakesPassButton, brakesFailButton, connectionsPassButton, connectionsFailButton, couplingsPassButton, couplingsFailButton, enginePassButton, engineFailButton, exteriorPassButton, exteriorFailButton, lightsPassButton, lightsFailButton;
	private Button interiorPassButton, interiorFailButton, lockingPinsPassButton, lockingPinsFailButton, tieDownsPassButton, tieDownsFailButton, wheelsPassButton, wheelsFailButton, windshieldPassButton, windshieldFailButton;
	private ImageView brakesFailIv, connectionsFailIv, couplingsFailIv, engineFailIv, exteriorFailIv, interiorFailIv, lightsFailIv, lockingPinsFailIv, tieDownsFailIv, wheelsFailIv, windshieldFailIv;

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



	}

}
