package com.rdc.project.filesystem.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.rdc.project.filesystem.MainActivity;
import com.rdc.project.filesystem.R;
import com.rdc.project.filesystem.base.BaseActivity;

import io.github.mthli.knife.KnifeText;

public class FileEditActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mTbTitle;
    private KnifeText mKnifeText;
    private ImageButton mIbBold;
    private ImageButton mIbItalic;
    private ImageButton mIbUnderline;
    private ImageButton mIbStrikeThrough;
    private ImageButton mIbBullet;
    private ImageButton mIbQuote;
    private ImageButton mIbLink;
    private ImageButton mIbClear;

    private String mContent;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_file_edit;
    }

    @Override
    protected void initData() {
        mContent = getIntent().getStringExtra("content");
    }

    @Override
    protected void initView() {
        mTbTitle = (Toolbar) findViewById(R.id.tb_title);
        mTbTitle.setTitle("编辑");
        setSupportActionBar(mTbTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTbTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mKnifeText = (KnifeText) findViewById(R.id.knife_text);
        mKnifeText.fromHtml(mContent);
        mIbBold = (ImageButton) findViewById(R.id.ib_bold);
        mIbItalic = (ImageButton) findViewById(R.id.ib_italic);
        mIbUnderline = (ImageButton) findViewById(R.id.ib_underline);
        mIbStrikeThrough = (ImageButton) findViewById(R.id.ib_strikethrough);
        mIbBullet = (ImageButton) findViewById(R.id.ib_bullet);
        mIbQuote = (ImageButton) findViewById(R.id.ib_quote);
        mIbLink = (ImageButton) findViewById(R.id.ib_link);
        mIbClear = (ImageButton) findViewById(R.id.ib_clear);
    }

    @Override
    protected void initListener() {
        mIbBold.setOnClickListener(this);
        mIbItalic.setOnClickListener(this);
        mIbUnderline.setOnClickListener(this);
        mIbStrikeThrough.setOnClickListener(this);
        mIbBullet.setOnClickListener(this);
        mIbQuote.setOnClickListener(this);
        mIbLink.setOnClickListener(this);
        mIbClear.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_file_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_undo:
                mKnifeText.undo();
                break;
            case R.id.action_redo:
                mKnifeText.redo();
                break;
            case R.id.action_save:
                Intent data = new Intent(this, MainActivity.class);
                data.putExtra("content", mKnifeText.toHtml());
                setResult(RESULT_OK, data);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_bold:
                mKnifeText.bold(!mKnifeText.contains(KnifeText.FORMAT_BOLD));
                break;
            case R.id.ib_italic:
                mKnifeText.italic(!mKnifeText.contains(KnifeText.FORMAT_ITALIC));
                break;
            case R.id.ib_underline:
                mKnifeText.underline(!mKnifeText.contains(KnifeText.FORMAT_UNDERLINED));
                break;
            case R.id.ib_strikethrough:
                mKnifeText.strikethrough(!mKnifeText.contains(KnifeText.FORMAT_STRIKETHROUGH));
                break;
            case R.id.ib_bullet:
                mKnifeText.bullet(!mKnifeText.contains(KnifeText.FORMAT_BULLET));
                break;
            case R.id.ib_quote:
                mKnifeText.quote(!mKnifeText.contains(KnifeText.FORMAT_QUOTE));
                break;
            case R.id.ib_link:
                showLinkDialog();
                break;
            case R.id.ib_clear:
                mKnifeText.clearFormats();
                break;
            default:
                break;
        }
    }

    private void showLinkDialog() {
        final int start = mKnifeText.getSelectionStart();
        final int end = mKnifeText.getSelectionEnd();
        View view = getLayoutInflater().inflate(R.layout.dialog_input, null, false);
        final EditText editText = view.findViewById(R.id.et_dialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("创建链接")
                .setCancelable(true)
                .setView(view)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String link = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(link)) {
                            return;
                        }
                        mKnifeText.link(link, start, end);
                    }
                });
        builder.create().show();
    }
}
