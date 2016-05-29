package com.example.administrator.xiaohua;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import IDao.UserInfo;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    String[] from = {"userPhoto", "userQQ", "deletButton"};
    int[] to = {R.id.login_userPhoto, R.id.login_userQQ, R.id.login_deleteButton};
    ArrayList<HashMap<String, Object>> list = null;


    private static boolean isVisible = true;         //ListView是否可见
    private static boolean isIndicatorUp = false;     //指示器的方向
    public static int currentSelectedPosition = -1;
    @Bind(R.id.login_picture)
    ImageView loginPicture;
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.Password)
    EditText qqPassword;
    @Bind(R.id.ListIndicator)
    ImageButton ListIndicator;
    @Bind(R.id.delete_button_edit)
    ImageButton deleteButtonEdit;
    @Bind(R.id.login)
    Button login;
    @Bind(R.id.loginList)
    ListView loginList;


    //用于记录当前选择的ListView中的QQ联系人条目的ID，如果是-1表示没有选择任何QQ账户，注意在向
    //List中添加条目或者删除条目时都要实时更新该currentSelectedPosition


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        list = new ArrayList<HashMap<String, Object>>();


        name.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (name.getText().toString().equals("") == false) {
                    deleteButtonEdit.setVisibility(View.VISIBLE);
                }

            }
        });

        deleteButtonEdit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                loginPicture.setImageResource(R.drawable.qqmain);
                name.setText("");
                currentSelectedPosition = -1;
                deleteButtonEdit.setVisibility(View.GONE);

            }
        });


        UserInfo user1 = new UserInfo(R.drawable.contact_0, "1234567", "21eqwre", R.drawable.deletebutton);
        UserInfo user2 = new UserInfo(R.drawable.contact_1, "10023455", "32rewqeaf", R.drawable.deletebutton);
        addUser(user1);
        addUser(user2);

        MyLoginListAdapter adapter = new MyLoginListAdapter(this, list, R.layout.layout_list_item, from, to);
        loginList.setAdapter(adapter);
        loginList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                loginPicture.setImageResource((Integer) list.get(arg2).get(from[0]));
                name.setText((String) list.get(arg2).get(from[1]));
                currentSelectedPosition = arg2;

                //相应完点击后List就消失，指示箭头反向！
                loginList.setVisibility(View.GONE);
                ListIndicator.setBackgroundResource(R.drawable.indicator_down);


            }


        });

        ListIndicator.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (isIndicatorUp) {
                    isIndicatorUp = false;
                    isVisible = false;
                    ListIndicator.setBackgroundResource(R.drawable.indicator_down);
                    loginList.setVisibility(View.GONE);   //让ListView列表消失

                } else {
                    isIndicatorUp = true;
                    isVisible = true;
                    ListIndicator.setBackgroundResource(R.drawable.indicator_up);
                    loginList.setVisibility(View.VISIBLE);
                }
            }

        });


    }

    private void addUser(UserInfo user) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(from[0], user.userPhoto);
        map.put(from[1], user.userQQ);
        map.put(from[2], user.deleteButtonRes);
        list.add(map);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN && isVisible) {
            int[] location = new int[2];
            //调用getLocationInWindow方法获得某一控件在窗口中左上角的横纵坐标
            loginList.getLocationInWindow(location);
            //获得在屏幕上点击的点的坐标
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (x < location[0] || x > location[0] + loginList.getWidth() ||
                    y < location[1] || y > location[1] + loginList.getHeight()) {
                isIndicatorUp = false;
                isVisible = false;


                ListIndicator.setBackgroundResource(R.drawable.indicator_down);
                loginList.setVisibility(View.GONE);   //让ListView列表消失，并且让游标向下指！

            }


        }


        return super.onTouchEvent(event);
    }

    /**
     * 为了便于在适配器中修改登录界面的Activity，这里把适配器作为
     * MainActivity的内部类，避免了使用Handler，简化代码
     *
     * @author DragonGN
     */

    public class MyLoginListAdapter extends BaseAdapter {

        protected Context context;
        protected ArrayList<HashMap<String, Object>> list;
        protected int itemLayout;
        protected String[] from;
        protected int[] to;


        public MyLoginListAdapter(Context context,
                                  ArrayList<HashMap<String, Object>> list, int itemLayout,
                                  String[] from, int[] to) {
            super();
            this.context = context;
            this.list = list;
            this.itemLayout = itemLayout;
            this.from = from;
            this.to = to;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        class ViewHolder {
            public ImageView userPhoto;
            public TextView userQQ;
            public ImageButton deleteButton;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder = null;
            /*
            currentPosition=position;
			不能使用currentPosition，因为每绘制完一个Item就会更新currentPosition
			这样得到的currentPosition将始终是最后一个Item的position
			*/

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(itemLayout, null);
                holder = new ViewHolder();
                holder.userPhoto = (ImageView) convertView.findViewById(to[0]);
                holder.userQQ = (TextView) convertView.findViewById(to[1]);
                holder.deleteButton = (ImageButton) convertView.findViewById(to[2]);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.userPhoto.setBackgroundResource((Integer) list.get(position).get(from[0]));
            holder.userQQ.setText((String) list.get(position).get(from[1]));
            holder.deleteButton.setBackgroundResource((Integer) list.get(position).get(from[2]));
            holder.deleteButton.setOnClickListener(new ListOnClickListener(position));

            return convertView;
        }

        class ListOnClickListener implements OnClickListener {

            private int position;


            public ListOnClickListener(int position) {
                super();
                this.position = position;
            }

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                list.remove(position);

                //如果删除的就是当前显示的账号，那么将主界面当前显示的头像设置回初始头像
                if (position == currentSelectedPosition) {
                    loginPicture.setImageResource(R.drawable.qqmain);
                    name.setText("123");
                    currentSelectedPosition = -1;
                } else if (position < currentSelectedPosition) {
                    currentSelectedPosition--;    //这里小于当前选择的position时需要进行减1操作
                }

                ListIndicator.setBackgroundResource(R.drawable.indicator_down);
                loginList.setVisibility(View.GONE);   //让ListView列表消失，并且让游标向下指！

                MyLoginListAdapter.this.notifyDataSetChanged();


            }

        }


    }

}
