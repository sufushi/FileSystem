package com.rdc.project.filesystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.rdc.project.filesystem.model.FileTreeItemHolder;
import com.rdc.project.filesystem.ui.FileEditActivity;
import com.rdc.project.filesystem.utils.FileSelectorUtil;
import com.rdc.project.filesystem.utils.FileTypeUtil;
import com.rdc.project.filesystem.utils.OpenFileUtil;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rdc.project.filesystem.constant.Constant.TYPE_FILE;
import static com.rdc.project.filesystem.constant.Constant.TYPE_FOLDER;

public class MainActivity extends AppCompatActivity implements TreeNode.TreeNodeClickListener, TreeNode.TreeNodeLongClickListener {

    private Toolbar mToolbar;
    private ConstraintLayout mClContainer;
    private AndroidTreeView mAndroidTreeView;
    private FileTreeItemHolder.FileTreeItem mFileTreeItem;
    private TreeNode mCopyTreeNode;
    private TreeNode mRoot;

    private List<String> mUserList;
    private Map<String, TreeNode> mRootMap;
    private List<View> mViewList;
    private View mCurView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        mUserList = new ArrayList<>();
        mRootMap = new HashMap<>();
        mViewList = new ArrayList<>();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("文件系统");
        setSupportActionBar(mToolbar);
        mClContainer = (ConstraintLayout) findViewById(R.id.cl_container);
        TreeNode root = TreeNode.root();
        TreeNode computerRoot = new TreeNode(new FileTreeItemHolder.FileTreeItem(R.drawable.ic_computer, TYPE_FOLDER, 2, "My Computer"));
        TreeNode userRoot = new TreeNode(new FileTreeItemHolder.FileTreeItem(R.drawable.ic_user, TYPE_FOLDER, 5, "Administrator"));
        TreeNode documents = new TreeNode(new FileTreeItemHolder.FileTreeItem(R.drawable.ic_folder, TYPE_FOLDER, 1, "My Documents"));
        TreeNode downloads = new TreeNode(new FileTreeItemHolder.FileTreeItem(R.drawable.ic_download_folder, TYPE_FOLDER, 5, "My Downloads"));
        TreeNode favorite = new TreeNode(new FileTreeItemHolder.FileTreeItem(R.drawable.ic_favorite, TYPE_FOLDER, 8, "My Favorite"));
        TreeNode media = new TreeNode(new FileTreeItemHolder.FileTreeItem(R.drawable.ic_media, TYPE_FOLDER, 0, "My Media"));
        for (int i = 0; i < 5; i++) {
            TreeNode file = new TreeNode(new FileTreeItemHolder.FileTreeItem(R.drawable.ic_file, TYPE_FILE, 0, "file" + i));
            downloads.addChild(file);
        }
        for (int i = 0; i < 8; i++) {
            TreeNode file = new TreeNode(new FileTreeItemHolder.FileTreeItem(R.drawable.ic_picture, TYPE_FILE, 0, "picture" + i));
            favorite.addChild(file);
        }
        documents.addChild(downloads);
        computerRoot.addChildren(documents, media);
        userRoot.addChildren(favorite);
        for (int i = 0; i < 4; i++) {
            TreeNode file = new TreeNode(new FileTreeItemHolder.FileTreeItem(R.drawable.ic_file, TYPE_FILE, 0, "file" + i));
            userRoot.addChild(file);
        }
        root.addChildren(computerRoot, userRoot);
        mAndroidTreeView = new AndroidTreeView(this, root);
        mAndroidTreeView.setDefaultAnimation(true);
        mAndroidTreeView.setDefaultContainerStyle(R.style.TreeNodeStyle);
        mAndroidTreeView.setDefaultViewHolder(FileTreeItemHolder.class);
        mAndroidTreeView.setDefaultNodeClickListener(this);
        mAndroidTreeView.setDefaultNodeLongClickListener(this);
        mAndroidTreeView.setUse2dScroll(false);
        mCurView = mAndroidTreeView.getView();
        mClContainer.addView(mCurView);
        mViewList.add(mCurView);
        mCopyTreeNode = root;
        mRoot = root;
        mUserList.add("admin");
        mRootMap.put("admin", mRoot);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_root_directory:
                createFolder(mRoot, null, true);
                break;
            case R.id.action_create_new_user:
                createNewUser();
                break;
            case R.id.action_switch_user:
                switchUser();
                break;
            case R.id.action_import_file:
                showFileChooser();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void switchUser() {
        int size = mUserList.size();
        String[] users = mUserList.toArray(new String[size]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("切换用户")
                .setCancelable(true)
                .setItems(users, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user = mUserList.get(which);
                        mRoot = mRootMap.get(user);
                        mAndroidTreeView.setRoot(mRoot);
                        mCopyTreeNode = mRoot;
                        mCurView.setVisibility(View.GONE);
                        mViewList.get(which).setVisibility(View.VISIBLE);
                        mCurView = mViewList.get(which);
                    }
                });
        builder.create().show();
    }

    private void createNewUser() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_input, null);
        final EditText editText = view.findViewById(R.id.et_dialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("创建新用户")
                .setCancelable(true)
                .setView(view)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user = editText.getText().toString();
                        if (mRootMap.containsKey(user)) {
                            Toast.makeText(MainActivity.this, "用户已经存在", Toast.LENGTH_SHORT).show();
                        } else {
                            mUserList.add(user);
                            TreeNode root = TreeNode.root();
                            mRootMap.put(user, root);
                            AndroidTreeView androidTreeView = new AndroidTreeView(MainActivity.this, root);
                            View v = androidTreeView.getView();
                            mClContainer.addView(v);
                            v.setVisibility(View.GONE);
                            mViewList.add(v);
                        }
                    }
                });
        builder.create().show();
    }

    @Override
    public void onClick(TreeNode node, Object value) {
        mCopyTreeNode = node;
        FileTreeItemHolder.FileTreeItem item = (FileTreeItemHolder.FileTreeItem) value;
        mFileTreeItem = item;
        if (item.type == TYPE_FILE) {
            Intent intent = OpenFileUtil.openFile(item.path);
            Log.e("error", "path=" + item.path);
            if (intent == null) {
                intent = new Intent(this, FileEditActivity.class);
                intent.putExtra("content", item.content);
                startActivityForResult(intent, 0);
            } else {
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onLongClick(final TreeNode node, final Object value) {
        String[] strings;
        if (((FileTreeItemHolder.FileTreeItem) value).type == TYPE_FOLDER) {
            strings = new String[]{"新建文件夹", "创建文件", "复制", "删除", "粘贴"};
        } else {
            strings = new String[]{"重命名", "属性", "复制", "删除"};
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                if (((FileTreeItemHolder.FileTreeItem) value).type == TYPE_FOLDER) {
                                    createFolder(node, value, false);
                                } else {
                                    renameFile(value);
                                }
                                break;
                            case 1:
                                if (((FileTreeItemHolder.FileTreeItem) value).type == TYPE_FOLDER) {
                                    createFile(node, value);
                                } else {

                                }
                                break;
                            case 2:
                                mFileTreeItem = (FileTreeItemHolder.FileTreeItem) value;
                                mCopyTreeNode = node;
                                break;
                            case 3:
                                mAndroidTreeView.removeNode(node);
                                break;
                            case 4:
                                if (mFileTreeItem == null) {
                                    return;
                                } else {
                                    if (check(node)) {
                                        Toast.makeText(MainActivity.this, "目标文件是源文件的子文件夹", Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    List<TreeNode> treeNodeList = new ArrayList<>();
                                    treeNodeList.add(mCopyTreeNode);
                                    paste(node, treeNodeList);

                                    FileTreeItemHolder.FileTreeItem item = (FileTreeItemHolder.FileTreeItem) value;
                                    item.size = node.size();
                                    item.fileTreeItemHolder.update();
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.create().show();
        return true;
    }

    private boolean check(TreeNode node) {
        return node == mCopyTreeNode || (node != null && check(node.getParent()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    mFileTreeItem.content = data.getStringExtra("content");
                    break;
                case 1:
                    Uri uri = data.getData();
                    String fileName = FileSelectorUtil.getPath(this, uri);
                    importFile(fileName);
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void paste(TreeNode parent, List<TreeNode> children) {
        for (TreeNode treeNode : children) {
            FileTreeItemHolder.FileTreeItem fileTreeItem =
                    (FileTreeItemHolder.FileTreeItem) treeNode.getValue();
            TreeNode node = new TreeNode(new FileTreeItemHolder.FileTreeItem(fileTreeItem.icon,
                    fileTreeItem.type, fileTreeItem.size, fileTreeItem.text, fileTreeItem.content));
            mAndroidTreeView.addNode(parent, node);
            if (fileTreeItem.type == TYPE_FOLDER) {
                paste(node, treeNode.getChildren());
            }
        }
    }

    private void createFile(final TreeNode node, final Object value) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_input, null);
        final EditText editText = view.findViewById(R.id.et_dialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("创建文件")
                .setCancelable(true)
                .setView(view)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String fileName = editText.getText().toString();
                        int resId = FileTypeUtil.getTypeResId(fileName);
                        TreeNode treeNode = new TreeNode(new FileTreeItemHolder.FileTreeItem(resId, TYPE_FILE, 0, fileName));
                        mAndroidTreeView.addNode(node, treeNode);
                        FileTreeItemHolder.FileTreeItem item = (FileTreeItemHolder.FileTreeItem) value;
                        item.size = node.size();
                        item.fileTreeItemHolder.update();
                    }
                });
        builder.create().show();
    }

    private void renameFile(final Object value) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_input, null);
        final EditText editText = view.findViewById(R.id.et_dialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("重命名文件")
                .setCancelable(true)
                .setView(view)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String fileName = editText.getText().toString();
                        FileTreeItemHolder.FileTreeItem item = (FileTreeItemHolder.FileTreeItem) value;
                        int index = item.text.lastIndexOf(".");
                        String postfix = item.text.substring(index);
                        item.text = fileName + postfix;
                        item.fileTreeItemHolder.update();
                    }
                });
        builder.create().show();
    }

    private void createFolder(final TreeNode node, final Object value, final boolean isRoot) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_input, null);
        final EditText editText = view.findViewById(R.id.et_dialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("创建文件夹")
                .setCancelable(true)
                .setView(view)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String folderName = editText.getText().toString();
                        TreeNode treeNode = new TreeNode(new FileTreeItemHolder.FileTreeItem(R.drawable.ic_folder, TYPE_FOLDER, 0, folderName));
                        mAndroidTreeView.addNode(node, treeNode);
                        if (!isRoot) {
                            FileTreeItemHolder.FileTreeItem item = (FileTreeItemHolder.FileTreeItem) value;
                            item.size = node.size();
                            item.fileTreeItemHolder.update();
                        }
                    }
                });
        builder.create().show();
    }

    private void showFileChooser() {
        if (mFileTreeItem.type == TYPE_FILE) {
            Toast.makeText(this, "请在目录下导入文件", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select a file to upLoad"), 1);
    }

    private void importFile(String fileName) {
        String path = "";
        if (fileName != null) {
            int index = fileName.lastIndexOf("/");
            path = fileName.substring(index + 1);
        }
        int resId = FileTypeUtil.getTypeResId(path);
        FileTreeItemHolder.FileTreeItem fileTreeItem = new FileTreeItemHolder.FileTreeItem(resId, TYPE_FILE, 0, path);
        fileTreeItem.path = fileName;
        TreeNode treeNode = new TreeNode(fileTreeItem);
        mAndroidTreeView.addNode(mCopyTreeNode, treeNode);
        if (mFileTreeItem != null) {
            mFileTreeItem.size = mCopyTreeNode.size();
            mFileTreeItem.fileTreeItemHolder.update();
        }
    }
}
