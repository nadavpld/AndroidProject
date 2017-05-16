package com.androidproject.androidproject.Infrastructure;

import android.content.Context;
import android.graphics.Bitmap;

import com.androidproject.androidproject.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;


public class ClarifaiHttpClient {

    public ClarifaiHttpClient(Context context) {
        Client = new ClarifaiBuilder(context.getString(R.string.clarifai_id), context.getString(R.string.clarifai_secret)).buildSync();
        if(Client.hasValidToken()) {

        }
    }

    private ClarifaiClient Client;

    //POST network request
    public String POST(Bitmap image) throws IOException {
        ConceptModel model = Client.getDefaultModels().apparelModel();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        ClarifaiResponse<List<ClarifaiOutput<Concept>>> Response = model.predict()
                .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(byteArray)))
                .executeSync();
        List<ClarifaiOutput<Concept>> predictions = Response.get();
        Concept concept = predictions.get(0).data().get(0);
        if(concept.value() < 0.95) {
            model = Client.getDefaultModels().foodModel();
            Response = model.predict()
                    .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(byteArray)))
                    .executeSync();
            predictions = Response.get();
            concept = predictions.get(0).data().get(0);
            if(concept.value() < 0.95) {
                model = Client.getDefaultModels().generalModel();
                Response = model.predict()
                        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(byteArray)))
                        .executeSync();
                predictions = Response.get();
                concept = predictions.get(0).data().get(0);
            }
        }
        if(concept.name().equals("no person")) {
            concept = predictions.get(0).data().get(1);
        }
        return concept.name();
    }
}