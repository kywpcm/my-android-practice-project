package com.pyo.android.widget.expension;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TextViewExpansion extends View{
	
	private Paint mTextPaint;
    private String mText;
    private int mAscent;
    
    public TextViewExpansion(Context context){
		super(context);	
		init();
	}
    
    public TextViewExpansion(Context context, AttributeSet attrs) {
		super(context, attrs);	
		init();
		
		//확장된 속성 집합을 레이아웃에서 가져 옴
		TypedArray attrsList = context.obtainStyledAttributes(attrs, R.styleable.TextViewExpansionStyle);
        
		//pyo_text로 선언된 속성의 해당 값을 가져와 세팅
        String textEAttr = attrsList.getString(R.styleable.TextViewExpansionStyle_pyo_text);
        if (textEAttr != null){
            setText(textEAttr.toString());
        }else{
        	setText("속성값 못 읽어옴!");
        }
        
        setTextColor(attrsList.getColor(R.styleable.TextViewExpansionStyle_pyo_textColor, 0xFF000000));

        int textSize = attrsList.getDimensionPixelOffset(R.styleable.TextViewExpansionStyle_pyo_textSize, 0);
        if (textSize > 0) {
            setTextSize(textSize);
        }
        
        //속성 집합을 재활용 하겠음
        attrsList.recycle();
	}
    
	public TextViewExpansion(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		mTextPaint = new Paint();
	    mTextPaint.setAntiAlias(true);
	    mTextPaint.setTextSize(16);
	    mTextPaint.setColor(0xff000000);
	    setPadding(2, 2, 2, 2);
	}
	
	public void setText(String text){
        mText = text;
        requestLayout();
        invalidate();
    }
	public void setTextSize(int size) {
        mTextPaint.setTextSize(size);
        //레이아웃에 적용 하도록 요청 함
        requestLayout();
        //레이아웃이 유효하지 않으니 다시 그리라는 메소드
        invalidate();
    }
    public void setTextColor(int color) {
        mTextPaint.setColor(color);
        requestLayout();
        invalidate();
    }
    //부모 레이아웃에 의해 호출되는 현재 자식의 적정 사이즈 및 모드를 재정의 함
    @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	//onMeasure에서 부모에게 응답을 보낼 때 사용하는 메소드
    	//이 부분을 호출하지 않으면 런타임에 IllegalStateException을 발생
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}
    //MeasureSpec를 분석하여 넓이를 분석하여 부모에게 넘김
    private int measureWidth(int measureSpec){
        int result = 0;
        //현재 Measure의 Mode를 리턴(상위 2비트)
        int specMode = MeasureSpec.getMode(measureSpec);
        //현재 Measure의 Size를 리턴(상위 2비트를 제외한 30 비트값)
        int specSize = MeasureSpec.getSize(measureSpec);
        //Mode가 EXACTLY 일 때 현재 사이즈로 계산
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
        	//아니면 좌/우(너비이므로)를 더한 값을 할당
            result = (int) mTextPaint.measureText(mText) + getPaddingLeft() + getPaddingRight();
            //자식이 가질 수 있는 최대값을 지정 함
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
    //MeasureSpec를 분석하여 높이를 분석하여 부모에게 넘김
	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		
		
		// mTextPaint.descent(); 플러스 값
		//현재 텍스트와 Typeface의 baseline의 above(위)값을 리턴
		// 마이너스 값
		mAscent = (int) mTextPaint.ascent();
		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			////아니면 위/아래(높이이므로)를 더한 값을 할당
			result = (int) (-mAscent + mTextPaint.descent()) + getPaddingTop() + getPaddingBottom();
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		//좌표를 잡고 부모를 기준으로 해당 텍스트를 그린다.
		canvas.drawText(mText, getPaddingLeft(), getPaddingTop() - mAscent, mTextPaint);
	}
}