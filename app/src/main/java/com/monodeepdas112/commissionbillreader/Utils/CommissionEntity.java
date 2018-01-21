package com.monodeepdas112.commissionbillreader.Utils;

import java.util.StringTokenizer;

public class CommissionEntity
{
    private String name;
    private String policyNum;
    private String plan;
    private String premDueDate;
    private String riskDate;
    private String cbo;
    private String adjustmentDate;
    private Double premium;
    private Double commision;

    public CommissionEntity(String line){
        StringTokenizer tokenizer=new StringTokenizer(line," ");
        int n=tokenizer.countTokens();

        try {
            if (n == 8) {
                name = null;
                policyNum = tokenizer.nextToken();
                plan = tokenizer.nextToken();
                premDueDate = tokenizer.nextToken();
                riskDate = tokenizer.nextToken();
                cbo = tokenizer.nextToken();
                adjustmentDate = tokenizer.nextToken();
                premium = Double.valueOf(tokenizer.nextToken());
                commision = Double.valueOf(tokenizer.nextToken());
            } else if (n > 8) {
                n = n - 8;
                String x = "";
                while (n > 0) {
                    x = x + " " + tokenizer.nextToken();
                    n--;
                }
                name = x;
                policyNum = tokenizer.nextToken();
                plan = tokenizer.nextToken();
                premDueDate = tokenizer.nextToken();
                riskDate = tokenizer.nextToken();
                cbo = tokenizer.nextToken();
                adjustmentDate = tokenizer.nextToken();
                premium = Double.valueOf(tokenizer.nextToken());
                commision = Double.valueOf(tokenizer.nextToken());
            }
        }catch (Exception e){
            name=null;
            policyNum=null;
            plan=null;
            premDueDate=null;
            riskDate=null;
            cbo=null;
            adjustmentDate=null;
            premium=null;
            commision=null;
        }
    }

    public String getName() {
        return name;
    }

    public String getRiskDate() {
        return riskDate;
    }

    public String getPlan() {
        return plan;
    }

    public String getCbo() {
        return cbo;
    }

    public Double getPremium() {
        return premium;
    }

    public Double getCommision() {
        return commision;
    }

    public String getAdjustmentDate() {
        return adjustmentDate;
    }

    public String getPolicyNum() {
        return policyNum;
    }

    public String getPremDueDate() {
        return premDueDate;
    }

    @Override
    public String toString() {
        return new String("Name : "+name+"\nPolicy Number : "+policyNum+"\nPlan : "+plan
                +"\nPremium Due Date : "+premDueDate+"\nRisk Date : "+riskDate+"\ncbo : "+cbo+"\nAdjDate : "+ adjustmentDate
                +"\nPremium : Rs "+premium+"\nCommission : Rs "+commision);
    }
}
