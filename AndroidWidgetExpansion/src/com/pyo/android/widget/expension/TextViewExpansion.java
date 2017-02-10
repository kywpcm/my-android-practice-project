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
		
		//Ȯ��� �Ӽ� ������ ���̾ƿ����� ���� ��
		TypedArray attrsList = context.obtainStyledAttributes(attrs, R.styleable.TextViewExpansionStyle);
        
		//pyo_text�� ����� �Ӽ��� �ش� ���� ������ ����
        String textEAttr = attrsList.getString(R.styleable.TextViewExpansionStyle_pyo_text);
        if (textEAttr != null){
            setText(textEAttr.toString());
        }else{
        	setText("�Ӽ��� �� �о��!");
        }
        
        setTextColor(attrsList.getColor(R.styleable.TextViewExpansionStyle_pyo_textColor, 0xFF000000));

        int textSize = attrsList.getDimensionPixelOffset(R.styleable.TextViewExpansionStyle_pyo_textSize, 0);
        if (textSize > 0) {
            setTextSize(textSize);
        }
        
        //�Ӽ� ������ ��Ȱ�� �ϰ���
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
        //���̾ƿ��� ���� �ϵ��� ��û ��
        requestLayout();
        //���̾ƿ��� ��ȿ���� ������ �ٽ� �׸���� �޼ҵ�
        invalidate();
    }
    public void setTextColor(int color) {
        mTextPaint.setColor(color);
        requestLayout();
        invalidate();
    }
    //�θ� ���̾ƿ��� ���� ȣ��Ǵ� ���� �ڽ��� ���� ������ �� ��带 ������ ��
    @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	//onMeasure���� �θ𿡰� ������ ���� �� ����ϴ� �޼ҵ�
    	//�� �κ��� ȣ������ ������ ��Ÿ�ӿ� IllegalStateException�� �߻�
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}
    //MeasureSpec�� �м��Ͽ� ���̸� �м��Ͽ� �θ𿡰� �ѱ�
    private int measureWidth(int measureSpec){
        int result = 0;
        //���� Measure�� Mode�� ����(���� 2��Ʈ)
        int specMode = MeasureSpec.getMode(measureSpec);
        //���� Measure�� Size�� ����(���� 2��Ʈ�� ������ 30 ��Ʈ��)
        int specSize = MeasureSpec.getSize(measureSpec);
        //Mode�� EXACTLY �� �� ���� ������� ���
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
        	//�ƴϸ� ��/��(�ʺ��̹Ƿ�)�� ���� ���� �Ҵ�
            result = (int) mTextPaint.measureText(mText) + getPaddingLeft() + getPaddingRight();
            //�ڽ��� ���� �� �ִ� �ִ밪�� ���� ��
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
    //MeasureSpec�� �м��Ͽ� ���̸� �м��Ͽ� �θ𿡰� �ѱ�
	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		
		
		// mTextPaint.descent(); �÷��� ��
		//���� �ؽ�Ʈ�� Typeface�� baseline�� above(��)���� ����
		// ���̳ʽ� ��
		mAscent = (int) mTextPaint.ascent();
		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			////�ƴϸ� ��/�Ʒ�(�����̹Ƿ�)�� ���� ���� �Ҵ�
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
		//��ǥ�� ��� �θ� �������� �ش� �ؽ�Ʈ�� �׸���.
		canvas.drawText(mText, getPaddingLeft(), getPaddingTop() - mAscent, mTextPaint);
	}
}