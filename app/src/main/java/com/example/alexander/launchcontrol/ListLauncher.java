package com.example.alexander.launchcontrol;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by alexander on 22.12.15.
 */
public class ListLauncher extends Fragment {

    private NerdAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(startupIntent, 0);
        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo a, ResolveInfo b) {
                PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(a.loadLabel(pm).toString(), b.loadLabel(pm).toString());
            }
        });
        adapter = new NerdAdapter(activities);
        Log.i("TAG", "onCreate");
//        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle bundle){
        View v = inflater.inflate(R.layout.list_launcher, parent, false);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) v.findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle("Control launcher");

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.list_view);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });





        Log.i("TAG", "onCreateView");

        return v;
    }



    private class NerdAdapter extends ArrayAdapter<ResolveInfo> {

        PackageManager pm = getActivity().getPackageManager();

        public NerdAdapter(List<ResolveInfo> activities) {
            super(getActivity(), 0, activities);
        }

        public View getView(int pos, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.icon_title, parent, false);
            final TextView tv = (TextView) convertView.findViewById(R.id.title);
            final ImageView iv = (ImageView) convertView.findViewById(R.id.image);
            ResolveInfo ri = getItem(pos);
            tv.setText(ri.loadLabel(pm));
            iv.setBackground(ri.loadIcon(pm));
            ViewGroup.LayoutParams params = iv.getLayoutParams();
            params.width = 100;
            params.height = 100;
            iv.setLayoutParams(params);

            Log.i("TAG", "getView");
/*
            ValueAnimator animatorAlpha = ValueAnimator.ofFloat(0.0f, 1.0f);
            animatorAlpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    tv.setAlpha((float) animation.getAnimatedValue());
                    iv.setAlpha((float) animation.getAnimatedValue());
                }
            });
            animatorAlpha.setDuration(300);

            ValueAnimator animatorTrans = ValueAnimator.ofFloat(400.0f, 0.0f);
            animatorTrans.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    tv.setTranslationX((float) animation.getAnimatedValue());
                    iv.setTranslationX((float) animation.getAnimatedValue());
                }
            });
            animatorTrans.setDuration(300);

            AnimatorSet set = new AnimatorSet();
            set.setInterpolator(new DecelerateInterpolator());
            set.play(animatorTrans).with(animatorAlpha);
            set.start();
*/
            return convertView;
        }
    }

}
