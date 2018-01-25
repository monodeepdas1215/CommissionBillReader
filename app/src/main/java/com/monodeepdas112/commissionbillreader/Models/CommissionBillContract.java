package com.monodeepdas112.commissionbillreader.Models;

import android.provider.BaseColumns;

/**
 * Created by monodeepdas112 on 22/1/18.
 */

public final class CommissionBillContract
{
    public static abstract class Files{

        public static final String TABLE_NAME = "tbl_Docs";
        public static final String ID = BaseColumns._ID;
        public static final String PATH = "path";
    }

    public static abstract class Entries{

        public static final String TABLE_NAME ="tbl_Commissions";
        public static final String ID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String POLICY_NUM = "policy_num";
        public static final String PLAN = "plan";
        public static final String PREM_DUE_DATE = "prem_due_date";
        public static final String RISK_DATE = "risk_date";
        public static final String CBO = "cbo";
        public static final String ADJUSTMENT_DATE = "adj_date";
        public static final String PREMIUM = "premium";
        public static final String COMMISSION = "commission";
    }
}
