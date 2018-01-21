package com.monodeepdas112.commissionbillreader.Utils;

import android.content.Context;
import android.os.AsyncTask;

import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripper;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by monodeep on 18-01-2018.
 */

public class PdfParserAsync extends AsyncTask<String,Void,List<CommissionEntity>>
{

    public ParserResponse delegade=null;

    public interface ParserResponse{
        void populateRecyclerAdapter(List<CommissionEntity> entities);
    }


    public PdfParserAsync(Context context){
        PDFBoxResourceLoader.init(context);
    }

    @Override
    protected List<CommissionEntity> doInBackground(String... strings) {

        File file=null;
        PDDocument document=null;
        List<CommissionEntity> list=null;
        try {

            file = new File(strings[0]);
            document=PDDocument.load(file);

            PDFTextStripper stripper=new PDFTextStripper();
            int pages = document.getNumberOfPages();

            stripper.setStartPage(2);
            //stripper.setEndPage(2);
            stripper.setEndPage(pages);

            String text = stripper.getText(document);

            list=getCommisionEntries(text);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<CommissionEntity> commissionEntities) {
        super.onPostExecute(commissionEntities);
        delegade.populateRecyclerAdapter(commissionEntities);
    }

    private List<CommissionEntity> getCommisionEntries(String text) {

        Scanner sc=new Scanner(text);
        String s;
        List<CommissionEntity> commissionEntityList =new ArrayList<>();
        CommissionEntity entity;
        while (sc.hasNextLine()){
            s=sc.nextLine();
            entity=new CommissionEntity(s);
            if(entity.getCommision()!=null)
                commissionEntityList.add(entity);
        }
        return commissionEntityList;
    }
}
