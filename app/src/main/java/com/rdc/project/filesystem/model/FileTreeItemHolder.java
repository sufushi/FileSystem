package com.rdc.project.filesystem.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdc.project.filesystem.R;
import com.rdc.project.filesystem.utils.DensityUtil;
import com.rdc.project.filesystem.utils.FileTypeUtil;
import com.unnamed.b.atv.model.TreeNode;

import static com.rdc.project.filesystem.constant.Constant.TYPE_FILE;
import static com.rdc.project.filesystem.constant.Constant.TYPE_FOLDER;

public class FileTreeItemHolder extends TreeNode.BaseNodeViewHolder<FileTreeItemHolder.FileTreeItem> {

    private ImageView mIvArrow;
    private ImageView mIvIcon;
    private TextView mTvText;
    private TextView mTvSize;

    private FileTreeItem mFileTreeItem;

    public FileTreeItemHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, FileTreeItem value) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_file_tree_item, null, false);
        mFileTreeItem = value;
        value.fileTreeItemHolder = this;
        mIvArrow = view.findViewById(R.id.iv_arrow);
        mIvIcon = view.findViewById(R.id.iv_icon);
        mTvText = view.findViewById(R.id.tv_text);
        mTvSize = view.findViewById(R.id.tv_size);
        if (value.type == TYPE_FOLDER) {
            mIvArrow.setVisibility(View.VISIBLE);
            mTvSize.setVisibility(View.VISIBLE);
            mTvSize.setText(String.valueOf(value.size));
            mIvIcon.setPadding(0, 0, 0, 0);
        } else if (value.type == TYPE_FILE) {
            mIvArrow.setVisibility(View.GONE);
            mTvSize.setVisibility(View.GONE);
            if (FileTypeUtil.isWpsFileType(value.text) || value.text.endsWith(".zip")
                    || value.text.endsWith(".rar") || value.text.endsWith(".html")) {
                int padding = (int) DensityUtil.px2dp(context, 12);
                mIvIcon.setPadding(padding, padding, padding, padding);
            } else {
                mIvIcon.setPadding(0, 0, 0, 0);
            }
        }
        mIvIcon.setImageResource(value.icon);
        mTvText.setText(value.text);
        return view;
    }

    @Override
    public void toggle(boolean active) {
        mIvArrow.setImageResource(active ? R.drawable.ic_arrow_down : R.drawable.ic_arrow_right);
    }

    public void update() {
        mTvText.setText(mFileTreeItem.text);
        mTvSize.setText(String.valueOf(mFileTreeItem.size));
    }

    public static class FileTreeItem {

        public int icon;
        public int type;
        public int size;
        public String text;
        public String content;
        public String path;
        public FileTreeItemHolder fileTreeItemHolder;

        public FileTreeItem(int icon, int type, int size, String text) {
            this.icon = icon;
            this.type = type;
            this.size = size;
            this.text = text;
            content = "";
            path = "";
        }

        public FileTreeItem(int icon, int type, int size, String text, String content) {
            this.icon = icon;
            this.type = type;
            this.size = size;
            this.text = text;
            this.content = content;
            path = "";
        }

    }

}
