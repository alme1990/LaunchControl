package com.example.alexander.launchcontrol;


/**
 * Created by alexander on 21.12.15.
 */

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.*;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentListLauncher extends ListFragment {

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
        NerdAdapter adapter = new NerdAdapter(activities);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle bundle){
        View v = inflater.inflate(R.layout.list_launcher, parent, false);

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

            return convertView;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        getListView().setAlpha(1);
    }

    @Override
    public void onListItemClick(final ListView l, final View v, int pos, long id) {
        final ResolveInfo ri = (ResolveInfo) l.getAdapter().getItem(pos);

        ValueAnimator animatorAlpha = ValueAnimator.ofFloat(1f, 0f);
        animatorAlpha.setDuration(300);
        animatorAlpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                l.setAlpha((Float) animation.getAnimatedValue());
            }
        });
        animatorAlpha.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {
/*                Intent i = new Intent(getActivity(), InfoLauncherActivity.class);
                i.putExtra(InfoLauncherFragment.KEY_POSITION, v.getY());
                i.putExtra(InfoLauncherFragment.KEY_NAME, ri.loadLabel(getActivity().getPackageManager()));
                i.putExtra(InfoLauncherFragment.KEY_PACKAGE, ri.activityInfo.packageName);
                i.putExtra(InfoLauncherFragment.KEY_NAME_INFO, ri.activityInfo.name);
                startActivity(i);*/
            }
            @Override
            public void onAnimationCancel(Animator animation) {
                animation.cancel();
            }
            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorAlpha.start();
    }
}
