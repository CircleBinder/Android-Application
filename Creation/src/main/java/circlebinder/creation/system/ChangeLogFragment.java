package circlebinder.creation.system;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.common.app.FragmentFactory;

import circlebinder.Legacy;
import circlebinder.common.app.FragmentTripper;
import circlebinder.common.dashboard.DashboardItem;
import circlebinder.creation.BaseFragment;
import circlebinder.creation.R;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public final class ChangeLogFragment extends BaseFragment implements Legacy {

    public static FragmentTripper tripper(FragmentManager fragmentManager) {
        return new FragmentTripper(fragmentManager, factory());
    }

    public static FragmentFactory<ChangeLogFragment> factory() {
        return new FragmentFactory<ChangeLogFragment>() {
            @Override
            public ChangeLogFragment create() {
                return newInstance();
            }
        };
    }

    private static ChangeLogFragment newInstance() {
        return new ChangeLogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.circlebinder_fragment_changelog, parent, false);
        StickyListHeadersListView changeLogsView = (StickyListHeadersListView)view.findViewById(
                R.id.circlebinder_fragment_changelog_list
        );
        ListItemAdapter adapter = new ListItemAdapter(getActivity());
        //TODO: 更新履歴を管理する用の仕組みを何か作る
        String sectionHeaderAsHistory = "2014-06-14 バージョン 0.4";
        adapter.add(
                new DashboardItem.Builder()
                        .setLabel("公式HPへのリンクを追加")
                        .setSectionTitle(sectionHeaderAsHistory)
                        .setSectionTitleId(sectionHeaderAsHistory.hashCode())
                        .build()
        );
        sectionHeaderAsHistory = "2014-06-08 バージョン 0.3";
        adapter.add(
                new DashboardItem.Builder()
                        .setLabel("サンシャインクリエイション 64のサークルデータを更新")
                        .setSectionTitle(sectionHeaderAsHistory)
                        .setSectionTitleId(sectionHeaderAsHistory.hashCode())
                        .build()
        );
        sectionHeaderAsHistory = "2014-06-08 バージョン 0.2";
        adapter.add(
                new DashboardItem.Builder()
                        .setLabel("出展ブロックによる絞り込みを実装")
                        .setSectionTitle(sectionHeaderAsHistory)
                        .setSectionTitleId(sectionHeaderAsHistory.hashCode())
                        .build()
        );
        adapter.add(
                new DashboardItem.Builder()
                        .setLabel("キーワード検索のリアルタイム絞り込みを実装")
                        .setSectionTitle(sectionHeaderAsHistory)
                        .setSectionTitleId(sectionHeaderAsHistory.hashCode())
                        .build()
        );
        sectionHeaderAsHistory = "2014-05-06 バージョン 0.1";
        adapter.add(
                new DashboardItem.Builder()
                        .setLabel("プレビュー版の公開")
                        .setSectionTitle(sectionHeaderAsHistory)
                        .setSectionTitleId(sectionHeaderAsHistory.hashCode())
                        .build()
        );
        changeLogsView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportActivity().getSupportActionBar().setTitle(R.string.circlebinder_navigation_change_log);
    }


}
