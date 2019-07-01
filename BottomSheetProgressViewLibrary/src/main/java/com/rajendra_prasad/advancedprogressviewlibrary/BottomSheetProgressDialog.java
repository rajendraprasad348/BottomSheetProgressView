package com.rajendra_prasad.advancedprogressviewlibrary;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;


import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class BottomSheetProgressDialog extends android.support.v7.widget.AppCompatTextView {
    public BottomSheetProgressDialog bottomSheetProgressDialog = null;
    private Dialog mDialog;
    private ProgressBar mProgressBar;
    private TextView progressText;
    private RelativeLayout rl_background_color, top_bar;
    private int BottomSheetProgress_TextColor, BottomSheetProgress_BackgroundColor;
    private String BottomSheetProgress_Message, BottomSheetProgress_ProgressBarColor;
    private float BottomSheetProgress_TextSize;
    private Drawable BottomSheetProgress_Drawable;
    private Animation rotation;
    private boolean BottomSheetProgress_Cancellable, BottomSheetProgress_Canceled_On_TouchOutside, BottomSheetProgress_Logo_Rotate = false, colorChange = false;

    public static final int NORMAL = 0;
    public static final int BOLD = 1;
    public static final int ITALIC = 2;
    public static final int BOLD_ITALIC = 3;

    public static final int RECTANGLE = 0;
    public static final int OVAL = 1;
    public static final int RING = 2;

    @FontFaceType
    private int BottomSheetProgress_Font_Face = NORMAL;
    private GradientDrawable drawable;
    private boolean barColor = false;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NORMAL, BOLD, ITALIC, BOLD_ITALIC})
    public @interface FontFaceType {
    }

    @ProgressStyle
    private int BottomSheetProgress_Default_Progress_Style = OVAL;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({RECTANGLE, OVAL, RING})
    public @interface ProgressStyle {
    }

    public BottomSheetProgressDialog(Context context) {
        super(context);
    }

    public BottomSheetProgressDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttr(context, attrs);

    }

    private void getAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BottomSheetProgressDialog, 0, 0);

        try {
            BottomSheetProgress_TextSize = typedArray.getDimensionPixelSize(R.styleable.BottomSheetProgressDialog_BottomSheetProgress_TextSize, context.getResources().getDimensionPixelSize(R.dimen.text_size_default));
            BottomSheetProgress_Message = typedArray.getString(R.styleable.BottomSheetProgressDialog_BottomSheetProgress_Message);
            BottomSheetProgress_TextColor = typedArray.getInt(R.styleable.BottomSheetProgressDialog_BottomSheetProgress_TextColor, Color.parseColor(getContext().getString(R.string.text_default_color)));
            BottomSheetProgress_BackgroundColor = typedArray.getColor(R.styleable.BottomSheetProgressDialog_BottomSheetProgress_BackgroundColor, getColor(R.color.background_default_color));
            BottomSheetProgress_Drawable = typedArray.getDrawable(R.styleable.BottomSheetProgressDialog_BottomSheetProgress_Icon);
            BottomSheetProgress_Cancellable = typedArray.getBoolean(R.styleable.BottomSheetProgressDialog_BottomSheetProgress_Cancellable, true);
            BottomSheetProgress_Canceled_On_TouchOutside = typedArray.getBoolean(R.styleable.BottomSheetProgressDialog_BottomSheetProgress_Canceled_On_Touch_Outside, true);

            BottomSheetProgress_Logo_Rotate = typedArray.getBoolean(R.styleable.BottomSheetProgressDialog_BottomSheetProgress_Logo_Rotate, false);
            BottomSheetProgress_Font_Face = typedArray.getInt(R.styleable.BottomSheetProgressDialog_BottomSheetProgress_Font_Face, Typeface.defaultFromStyle(Typeface.NORMAL).getStyle());
            BottomSheetProgress_Default_Progress_Style = typedArray.getInt(R.styleable.BottomSheetProgressDialog_BottomSheetProgress_Default_Progress_Style, 1);
            BottomSheetProgress_ProgressBarColor = typedArray.getString(R.styleable.BottomSheetProgressDialog_BottomSheetProgress_ProgressBar_Color);

        } finally {
            typedArray.recycle();
        }

        showProgress(context);

    }


    public void showProgress(Context context) {
        mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setContentView(R.layout.bottom_sheet_dialog_layout);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        init(context);

        LayerDrawable layers = (LayerDrawable) mProgressBar.getBackground();
        RotateDrawable rotate = (RotateDrawable) layers.findDrawableByLayerId(R.id.shape_id);
        drawable = (GradientDrawable) rotate.getDrawable();

        setCanaraBackgroundColor(BottomSheetProgress_BackgroundColor);
        setCanaraProgressTextColor(BottomSheetProgress_TextColor);
        setCanaraProgressTextSize(BottomSheetProgress_TextSize);
        setCanaraProgressCancellable(BottomSheetProgress_Cancellable);
        setCanaraProgressCanceledOnTouchOutside(BottomSheetProgress_Canceled_On_TouchOutside);
        setCanaraProgressFontFace(BottomSheetProgress_Font_Face);
        setCanaraProgressStyle(BottomSheetProgress_Default_Progress_Style);


        if (BottomSheetProgress_Message != null) {
            setCanaraProgressMessage(BottomSheetProgress_Message);
        } else {
            setCanaraProgressMessage(getContext().getString(R.string.default_message));
        }

        if (BottomSheetProgress_Drawable != null) {
            setCanaraProgressBackgroundDrawable(BottomSheetProgress_Drawable);
            barColor = false;
        } else {
            setCanaraProgressBackgroundDrawable(getResources().getDrawable(R.drawable.progress));
            barColor = true;
        }

        if (barColor) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (BottomSheetProgress_ProgressBarColor == null) {
                    setCanaraProgressBarColor(getContext().getString(R.string.default_progress_bar_color));
                } else {
                    setCanaraProgressBarColor(BottomSheetProgress_ProgressBarColor);
                }
            }
        }

        top_bar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BottomSheetProgress_Canceled_On_TouchOutside) {
                    hideProgress();
                }
            }
        });


        if (mDialog != null)
            mDialog.show();
    }


    private void init(Context context) {
        progressText = (TextView) mDialog.findViewById(R.id.text);
        rl_background_color = mDialog.findViewById(R.id.rl_background_color);
        top_bar = mDialog.findViewById(R.id.top_bar);
        mProgressBar = (ProgressBar) mDialog.findViewById(R.id.myprogress);

        rotation = AnimationUtils.loadAnimation(context, R.anim.rotate);
    }


    public BottomSheetProgressDialog setCanaraProgressBackgroundDrawable(Drawable drawable) {
        this.BottomSheetProgress_Drawable = drawable;
        mProgressBar.setIndeterminateDrawable(BottomSheetProgress_Drawable);

        if (BottomSheetProgress_Logo_Rotate) {
            colorChange = true;
            mProgressBar.startAnimation(rotation);
        }

        return this;
    }


    public BottomSheetProgressDialog setCanaraBackgroundColor(int color) {
        this.BottomSheetProgress_BackgroundColor = color;
        rl_background_color.setBackgroundColor(BottomSheetProgress_BackgroundColor);
        return this;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BottomSheetProgressDialog setCanaraProgressBarColor(String color) {
        this.BottomSheetProgress_ProgressBarColor = color;
        mProgressBar.setIndeterminateTintList(ColorStateList.valueOf(Color.parseColor(BottomSheetProgress_ProgressBarColor)));
        return this;
    }


    public BottomSheetProgressDialog setCanaraProgressTextColor(int color) {
        this.BottomSheetProgress_TextColor = color;
        progressText.setTextColor(BottomSheetProgress_TextColor);
        return this;
    }


    public BottomSheetProgressDialog setCanaraProgressMessage(String Message) {
        this.BottomSheetProgress_Message = Message;
        progressText.setText(BottomSheetProgress_Message);
        return this;
    }

    public BottomSheetProgressDialog setCanaraProgressTextSize(float size) {
        this.BottomSheetProgress_TextSize = size;
        progressText.setTextSize(BottomSheetProgress_TextSize);
        return this;
    }

    public BottomSheetProgressDialog setCanaraProgressCancellable(boolean value) {
        this.BottomSheetProgress_Cancellable = value;
        mDialog.setCancelable(BottomSheetProgress_Cancellable);

        return this;
    }

    public BottomSheetProgressDialog setCanaraProgressCanceledOnTouchOutside(boolean value) {
        this.BottomSheetProgress_Canceled_On_TouchOutside = value;
        mDialog.setCanceledOnTouchOutside(BottomSheetProgress_Canceled_On_TouchOutside);
        return this;
    }


    public BottomSheetProgressDialog setCanaraProgressFontFace(int value) {
        this.BottomSheetProgress_Font_Face = value;
        try {
            if (BottomSheetProgress_Font_Face == BOLD_ITALIC) {
                progressText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
            } else if (BottomSheetProgress_Font_Face == ITALIC) {
                progressText.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            } else if (BottomSheetProgress_Font_Face == BOLD) {
                progressText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                progressText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return this;
    }


    public BottomSheetProgressDialog setCanaraProgressStyle(int value) {
        this.BottomSheetProgress_Default_Progress_Style = value;
        try {
            if (BottomSheetProgress_Default_Progress_Style == RECTANGLE) {
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setUseLevel(true);

            } else if (BottomSheetProgress_Default_Progress_Style == OVAL) {
                drawable.setShape(GradientDrawable.OVAL);
                drawable.setUseLevel(true);
            } else {
                drawable.setShape(GradientDrawable.RING);
                drawable.setUseLevel(true);
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return this;
    }


    private int getColor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getContext().getColor(id);
        } else {
            return getResources().getColor(id);
        }
    }

    public void hideProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
