package com.mitrakreasindo.pos.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mitrakreasindo.pos.main.LoginActivity;
import com.mitrakreasindo.pos.main.MainActivity;
import com.mitrakreasindo.pos.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by error on 15/05/17.
 */

public class MaintenanceFragment extends Fragment {

//    @BindView(R.id.mtnc_users)
//    CardView mtncUsers;
//    @BindView(R.id.mtnc_roles)
//    CardView mtncRoles;
//    @BindView(R.id.mtnc_taxes)
//    CardView mtncTaxes;
//    @BindView(R.id.mtnc_tax_categories)
//    CardView mtncTaxCategories;
//    @BindView(R.id.mtnc_consm_tax_categories)
//    CardView mtncConsmTaxCategories;
//    @BindView(R.id.mtnc_resources)
//    CardView mtncResources;
//    @BindView(R.id.mtnc_locations)
//    CardView mtncLocations;
//    @BindView(R.id.mtnc_floors)
//    CardView mtncFloors;
//    @BindView(R.id.mtnc_tables)
//    CardView mtncTables;
//    @BindView(R.id.mtnc_reset_counter)
//    CardView mtncResetCounter;
//    @BindView(R.id.mtnc_release_tab_lock)
//    CardView mtncReleaseTabLock;
//    @BindView(R.id.mtnc_report_users)
//    CardView mtncReportUsers;
//    @BindView(R.id.mtnc_sales_user)
//    CardView mtncSalesUser;
//    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

//        View view = inflater.inflate(R.layout.fragment_maintenance, container, false);

//        unbinder = ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
//        return super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_maintenance, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }
}
