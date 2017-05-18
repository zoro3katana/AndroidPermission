package zoro3katana.permission6;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nttrung on 5/15/2017.
 */

public class AppInfoAdapter extends BaseAdapter {
    private List<ApplicationInfo> appsList = null;
    private Context context;
    private PackageManager packageManager;

    public AppInfoAdapter(Context context, int textViewResourceId,
                          List<ApplicationInfo> appsList) {
        this.context = context;
        this.appsList = appsList;
        packageManager = context.getPackageManager();
    }

    @Override
    public int getCount() {
        return ((null != appsList) ? appsList.size() : 0);
    }

    @Override
    public ApplicationInfo getItem(int position) {
        return ((null != appsList) ? appsList.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.app_info_list_row, null);
        }

        ApplicationInfo applicationInfo = appsList.get(position);
        if (null != applicationInfo) {
            TextView appName = (TextView) view.findViewById(R.id.app_name);
            TextView packageName = (TextView) view.findViewById(R.id.app_package);
            TextView appVersion = (TextView) view.findViewById(R.id.app_version);
            TextView appSource = (TextView) view.findViewById(R.id.app_source);
            ImageView iconview = (ImageView) view.findViewById(R.id.app_icon);

            appName.setText(applicationInfo.loadLabel(packageManager));
            packageName.setText(applicationInfo.packageName);

            // get app version
            PackageInfo pInfo = null;
            try {
                pInfo = packageManager.getPackageInfo(applicationInfo.packageName, 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            String version = pInfo.versionName;
            // set app version
            appVersion.setText(version);

            appSource.setText(applicationInfo.sourceDir);
            iconview.setImageDrawable(applicationInfo.loadIcon(packageManager));
        }
        return view;
    }
}
