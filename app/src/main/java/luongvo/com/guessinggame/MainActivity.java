package luongvo.com.guessinggame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int[] img = new int[] {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5};
    private String[] ans = new String[] {"trả lời mẫu số 1", "trả lời mẫu số 2", "trả lời mẫu số 3", "trả lời mẫu số 4", "trả lời mẫu số 5"};
    private int curQuestionID = 0;
    private int score = 0;

    ImageView imageView;
    TextView textView;
    CheckBox checkBox;
    EditText editBetScore, editAns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();
        displayQuestion(0);
    }

    private void displayQuestion(int id) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), img[id]);
        imageView.setImageBitmap(bmp);
        checkBox.setChecked(false);
        editBetScore.setText("");
        editAns.setText("");
    }

    private void initComponent() {
        imageView = (ImageView)findViewById(R.id.imageView);
        textView = (TextView)findViewById(R.id.textViewScore);
        checkBox = (CheckBox)findViewById(R.id.checkbox);
        editBetScore = (EditText)findViewById(R.id.edtbetScore);
        editAns = (EditText)findViewById(R.id.edtAns);
    }

    public void onClickButtonAns(View view) {
        boolean betFlag = false;
        int betScore = 0;

        if (checkBox.isChecked()) {
            betFlag = true;
            try {
                betScore = Integer.parseInt(editBetScore.getText().toString());
                if (betScore < 0 || betScore > score) {
                    Toast.makeText(this, "Cược phải bằng hoặc bé hơn ", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (Exception e) {
                Toast.makeText(this, "Số cược không hợp lệ", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return;
            }
        }

        String answ = editAns.getText().toString();
        if (answ.length() == 0) {
            Toast.makeText(this, "Chưa nhập câu trả lời", Toast.LENGTH_SHORT).show();
            return;
        }

        if (answ.toLowerCase().equals(ans[curQuestionID])) {
            Toast.makeText(this, "Chúc mừng bạn!", Toast.LENGTH_SHORT).show();
            score += 50;
            if (betFlag) score += betScore;
            curQuestionID++;
        } else {
            Toast.makeText(this, "Câu trả lời chưa đúng nhé!", Toast.LENGTH_SHORT).show();
            score -= 50;
            if (betFlag) score -= betScore;
            score = score <= 0 ? 0 : score;
        }
        textView.setText(getString(R.string.score, score));
        displayQuestion(curQuestionID);
    }
}
