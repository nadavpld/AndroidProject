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
        final ConceptModel generalModel = Client.getDefaultModels().generalModel();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        // Use this model to predict, with the image that the user just selected as the input
        ClarifaiResponse<List<ClarifaiOutput<Concept>>> Response = generalModel.predict()
                .withInputs(ClarifaiInput.forImage(ClarifaiImage.of("https://images-na.ssl-images-amazon.com/images/I/814dF6jfIoL._SL1500_.jpg")))
                .executeSync();
        List<ClarifaiOutput<Concept>> predictions = Response.get();
        Concept concept = predictions.get(0).data().get(0);
        return concept.name();
    }
}