package com.p2017capstone2.ipsimplement;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by kuman514 on 2017-12-14.
 */

public class TempActivity extends Activity {
    // 화면 전환 및 참고용 임시 액티비티
    // 이 액티비티는 버튼을 누르면 원래 액티비티로 돌아갑니다.

    Button backButton;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        // OnCreate 기본 동작
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_activity_layout);
        // ==================================================

        backButton = (Button) findViewById(R.id.tmp_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TempActivity.this.finish();
                // 원래 액티비티로 돌아가고자 하는 경우, 액티비티명.this.finish()를 실행해주세요.
            }
        });
    }

}
