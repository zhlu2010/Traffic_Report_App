package com.laioffer.matrix;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReportDialog extends Dialog {

    private int cx;
    private int cy;
    private RecyclerView mRecyclerView;
    private ReportRecyclerViewAdapter mRecyclerViewAdapter;
    private ViewSwitcher mViewSwitcher;
    private String mEventype;
    private ImageView mImageCamera;
    private Button mBackButton;
    private Button mSendButton;
    private EditText mCommentEditText;
    private ImageView mEventTypeImg;
    private TextView mTypeTextView;
    private DialogCallBack mDialogCallBack;


    public ReportDialog(@NonNull Context context) {
        this(context, R.style.MyAlertDialogStyle);
    }

    public interface DialogCallBack {
        void onSubmit(String editString, String event_type);
        void startCamera();
    }

    public ReportDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static ReportDialog newInstance(Context context, int cx, int cy, DialogCallBack dialogCallBack) {
        ReportDialog dialog = new ReportDialog(context, R.style.MyAlertDialogStyle);
        dialog.cx = cx;
        dialog.cy = cy;
        dialog.mDialogCallBack = dialogCallBack;
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View dialogView = View.inflate(getContext(), R.layout.dialog, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(dialogView);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                animateDialog(dialogView, true);
            }
        });

        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if(keyCode == KeyEvent.KEYCODE_BACK) {
                    animateDialog(dialogView, false);
                    return true;
                }
                return false;
            }
        });
        setupRecyclerView(dialogView);
        setUpEventSpecs(dialogView);
        mViewSwitcher = dialogView.findViewById(R.id.viewSwitcher);

        Animation slide_in_left = AnimationUtils.loadAnimation(getContext(),
                android.R.anim.slide_in_left);
        Animation slide_out_right = AnimationUtils.loadAnimation(getContext(),
                android.R.anim.slide_out_right);
        mViewSwitcher.setInAnimation(slide_in_left);
        mViewSwitcher.setOutAnimation(slide_out_right);

    }

    private void setupRecyclerView(View dialogView) {
        mRecyclerView = dialogView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerViewAdapter = new ReportRecyclerViewAdapter(getContext(), Config.listItems);
        mRecyclerViewAdapter.setClickListener(new ReportRecyclerViewAdapter
                .OnClickListener() {
            @Override
            public void setItem(String item) {
                // for switch item
                showNextViewSwitcher(item);
            }
        });
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void showNextViewSwitcher(String item) {
        mEventype = item;
        if (mViewSwitcher != null) {
            mViewSwitcher.showNext();
            mTypeTextView.setText(mEventype);
            mEventTypeImg.setImageDrawable(ContextCompat.getDrawable(getContext(),Config.trafficMap.get(mEventype)));
        }
    }

    private void setUpEventSpecs(final View dialogView) {
        mImageCamera = dialogView.findViewById(R.id.event_camera_img);
        mBackButton = dialogView.findViewById(R.id.event_back_button);
        mSendButton = dialogView.findViewById(R.id.event_send_button);
        mCommentEditText = dialogView.findViewById(R.id.event_comment);
        mEventTypeImg = dialogView.findViewById(R.id.event_type_img);
        mTypeTextView = dialogView.findViewById(R.id.event_type);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDialogCallBack != null) {
                    mDialogCallBack.onSubmit(mCommentEditText.getText().toString(), mEventype);
                }
            }
        });
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mViewSwitcher != null) {
                    mViewSwitcher.showPrevious();
                }
            }
        });
        mImageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogCallBack.startCamera();
            }
        });
    }


    private void animateDialog(View dialogView, boolean open) {
        final View view = dialogView.findViewById(R.id.dialog);
        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);

        if(open) {
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);
            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(500);
            revealAnimator.start();
        } else {
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    view.setVisibility(View.INVISIBLE);
                    dismiss();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.setDuration(500);
            anim.start();
        }
    }

    public void updateImage(Bitmap bitmap) {
        mImageCamera.setImageBitmap(bitmap);
    }


}
