package juli.bindi.guessinggame;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.Random;

public class Level2 extends AppCompatActivity {

    Dialog dialog;
    Dialog dialogEnd;

    // переменная для левой/правой картинки
    public int numLeft;
    public int numRight;
    Array array = new Array();
    Random random = new Random();
    public int count = 0; // счетчик правильных ответов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        //создаем переменную текст левелс
        TextView textlevels = findViewById(R.id.text_levels);
        textlevels.setText(R.string.level1); //Установили текст

        final ImageView img_left = (ImageView) findViewById(R.id.img_left);
        // код, который скругляет углы левой картинки
        img_left.setClipToOutline(true);

        final ImageView img_right = (ImageView) findViewById(R.id.img_right);
        // код, который скругляет углы правой картинки
        img_right.setClipToOutline(true);

        // Путь к левой/правой TextViev
        final TextView text_left = findViewById(R.id.text_left);
        final TextView text_right = findViewById(R.id.text_right);

        //Развернуть игру на весь экран - начало
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //конец

        //вызов диалогового окна в начале игры:
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);// скрываем заголовок
        dialog.setContentView(R.layout.previewdialog);//путь к макету диалогового окна
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// прозрачный фон дмалогового окна
        dialog.setCancelable(false); // окно нельзя закрыть системной кнопкой назад

        //устанавливаем картинку в диалоговое окно
        ImageView previewing = dialog.findViewById(R.id.previewimg);
        previewing.setImageResource(R.drawable.previewimgtwo);

        //Устанавливаем описание задания начало
        TextView textdescription = dialog.findViewById(R.id.textdescription);
        textdescription.setText(R.string.leveltwo);

        // кнопка, которая закрывает диалоговое окно начало
        TextView btnclose = dialog.findViewById(R.id.btncloses);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // вернуться назад к выбору уровня
                    Intent intent = new Intent(getBaseContext(), GameLevels.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                }
                dialog.dismiss();//закрываем диалоговое окно
            }
        });
        // конец
        //кнопка продолжить
        Button btncontinue = dialog.findViewById(R.id.btncontinue);
        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();// показать диалоговое окно

        //_______________________________________
        //вызов диалогового окна в конце игры:
        dialogEnd = new Dialog(this);
        dialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE);// скрываем заголовок
        dialogEnd.setContentView(R.layout.dialogend);//путь к макету диалогового окна
        dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// прозрачный фон дмалогового окна
        dialogEnd.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        dialogEnd.setCancelable(false); // окно нельзя закрыть системной кнопкой назад
        //интересный факт начало
        TextView textdescriptionEnd = dialogEnd.findViewById(R.id.textdescriptionEnd);
        textdescriptionEnd.setText(R.string.leveltwoEnd);
        // конец

        // кнопка, которая закрывает диалоговое окно начало
        TextView btnclose2 = dialogEnd.findViewById(R.id.btncloses);
        btnclose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // вернуться назад к выбору уровня
                    Intent intent = new Intent(getBaseContext(), GameLevels.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                }
                dialogEnd.dismiss();//закрываем диалоговое окно
            }
        });
        // конец
        //кнопка продолжить
        Button btncontinue2 = dialogEnd.findViewById(R.id.btncontinue);
        btncontinue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getBaseContext(), Level2.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                }
                dialogEnd.dismiss();
            }
        });
        //________________________________________

        //кнопка "Назад"
        Button btnback = findViewById(R.id.button_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // вернуться назад к выбору уровня
                    Intent intent = new Intent(getBaseContext(), GameLevels.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                }
            }
        });

        //Массив для прогресса игры начало
        final int[] progress = {
                R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5,
                R.id.point6, R.id.point7, R.id.point8, R.id.point9, R.id.point10,
                R.id.point11, R.id.point12, R.id.point13, R.id.point14, R.id.point15,
                R.id.point16, R.id.point17, R.id.point18, R.id.point19, R.id.point20,

        };
        // подключаем анимацию
        final Animation animation = AnimationUtils.loadAnimation(Level2.this, R.anim.alpha);

        numLeft = random.nextInt(10); // рандом от 0 до 9
        img_left.setImageResource(array.images2[numLeft]);// достаем из массива картинку
        text_left.setText(array.text2[numLeft]);

        numRight = random.nextInt(10); // рандом от 0 до 9
        while (numLeft == numRight) {
            numRight = random.nextInt(10);
        }
        img_right.setImageResource(array.images2[numRight]);// достаем из массива картинку
        text_right.setText(array.text2[numRight]);

        //обрабатывает нажатие на левую картинку
        img_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // условие касания картинки
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // если коснулся картинки
                    img_right.setEnabled(false);//блокируем правую
                    if (numLeft > numRight) {
                        img_left.setImageResource(R.drawable.img_true);
                    } else {
                        img_left.setImageResource(R.drawable.img_false);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // если отпустил палец
                    if (numLeft > numRight) {
                        //если левая картинка больше
                        if (count < 20) {
                            count++;
                        }
                        //закрашиваем прогресс
                        for (int i = 0; i < 20; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        //определяем правильные ответы и закрашиваем зеленым
                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    } else {
                        //если левая картинка меньше
                        if (count > 1) {
                            count -= 2;
                        }
                        for (int i = 0; i < 19; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        //определяем правильные ответы и закрашиваем зеленым
                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    }
                    if (count == 20) {
                        //выход из уровня
                        dialogEnd.show();
                    } else {
                        numLeft = random.nextInt(10); // рандом от 0 до 9
                        img_left.setImageResource(array.images2[numLeft]);// достаем из массива картинку
                        img_left.startAnimation(animation);
                        text_left.setText(array.text2[numLeft]);

                        numRight = random.nextInt(10); // рандом от 0 до 9
                        while (numLeft == numRight) {
                            numRight = random.nextInt(10);
                        }
                        img_right.setImageResource(array.images2[numRight]);// достаем из массива картинку
                        img_right.startAnimation(animation);
                        text_right.setText(array.text2[numRight]);
                        img_right.setEnabled(true);//включаем правую
                    }
                }

                return true;
            }
        });

        //обрабатывает нажатие на правую картинку
        img_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // условие касания картинки
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // если коснулся картинки
                    img_left.setEnabled(false);//блокируем левую
                    if (numLeft < numRight) {
                        img_right.setImageResource(R.drawable.img_true);
                    } else {
                        img_right.setImageResource(R.drawable.img_false);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // если отпустил палец
                    if (numLeft < numRight) {
                        //если правая картинка больше
                        if (count < 20) {
                            count++;
                        }
                        //закрашиваем прогресс
                        for (int i = 0; i < 20; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        //определяем правильные ответы и закрашиваем зеленым
                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    } else {
                        //если правая картинка меньше
                        if (count > 1) {
                            count -= 2;
                        }
                        for (int i = 0; i < 19; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        //определяем правильные ответы и закрашиваем зеленым
                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    }
                    if (count == 20) {
                        //выход из уровня
                        dialogEnd.show();
                    } else {
                        numLeft = random.nextInt(10); // рандом от 0 до 9
                        img_left.setImageResource(array.images2[numLeft]);// достаем из массива картинку
                        img_left.startAnimation(animation);
                        text_left.setText(array.text2[numLeft]);

                        numRight = random.nextInt(10); // рандом от 0 до 9
                        while (numLeft == numRight) {
                            numRight = random.nextInt(10);
                        }
                        img_right.setImageResource(array.images2[numRight]);// достаем из массива картинку
                        img_right.startAnimation(animation);
                        text_right.setText(array.text2[numRight]);
                        img_left.setEnabled(true);//включаем левую
                    }
                }

                return true;
            }
        });
    }

    // системная кнопка "Назад" - начало
    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(getBaseContext(), GameLevels.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
        }
    }
    // конец
}
