package com.ehsandev.cs2340.api;


import com.ehsandev.cs2340.model.PurityReport;
import com.ehsandev.cs2340.model.Response;
import com.ehsandev.cs2340.model.SourceReport;
import com.mashape.relocation.impl.nio.reactor.ExceptionEvent;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReportHandler {
    /**
     * Post Source Report creation to API Endpoint
     * @param sourceReport Source Report to save
     * @param cookie Authentication cookie
     * @return API Response status
     */
    public static Response<String> postSourceReport(SourceReport sourceReport, String cookie) {
        try {
            JSONObject jsonResponse = Unirest.post(Constants.URL_BASE + "/api/report/source").header("Cookie", cookie)
                    .field("lat", sourceReport.getLat())
                    .field("long", sourceReport.getLon())
                    .field("type", sourceReport.getType())
                    .field("condition", sourceReport.getCondition())
                    .asJson().getBody().getObject();
            if (jsonResponse.has("message")) {
                return new Response<>(jsonResponse.getInt("success"), jsonResponse.getString("message"), null);
            } else {
                return new Response<>(jsonResponse.getInt("success"), "", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(0, "", null);
        }
    }

    /**
     * Get list of source reports submitted
     * @param cookie Authentication cookie
     * @return API Response with list of source reports if successful
     */
    public static Response<SourceReport[]> getSourceReports(String cookie) {
        try {
            JSONArray jsonResponse = Unirest.get(Constants.URL_BASE + "/api/report/source").header("Cookie", cookie)
                    .asJson().getBody().getArray();
            SourceReport[] sourceReports = new SourceReport[jsonResponse.length()];
            for (int i = 0; i < jsonResponse.length(); i++){
                JSONObject obj = jsonResponse.getJSONObject(i);
                sourceReports[i] = new SourceReport(obj.getString("name"), obj.getDouble("lat"),
                        obj.getDouble("long"), obj.getString("type"), obj.getString("condition"), obj.getString("_id"), obj.getString("date"));
            }
            return new Response<>(1, "", sourceReports);
        } catch (UnirestException e) {
            e.printStackTrace();
            return new Response<>(0, "", null);
        }
        catch (Exception e){
            e.printStackTrace();
            return new Response<>(0, "", null);
        }
    }

    /**
     * Post Purity Report creation to API Endpoint
     * @param purityReport Purity Report to save
     * @param cookie Authentication cookie
     * @return API Response status
     */
    public static Response<String> postPurityReport(PurityReport purityReport, String cookie) {
        try {
            JSONObject jsonResponse = Unirest.post(Constants.URL_BASE + "/api/report/purity").header("Cookie", cookie)
                    .field("lat", purityReport.getLat())
                    .field("long", purityReport.getLon())
                    .field("condition", purityReport.getCondition())
                    .field("contaminant", purityReport.getContaminant())
                    .field("virus", purityReport.getVirus())
                    .asJson().getBody().getObject();
            if (jsonResponse.has("message")) {
                return new Response<>(jsonResponse.getInt("success"), jsonResponse.getString("message"), null);
            } else {
                return new Response<>(jsonResponse.getInt("success"), "", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(0, "", null);
        }
    }

    /**
     * Get list of purity reports submitted
     * @param cookie Authentication cookie
     * @return API Response with list of quality reports if successful
     */
    public static Response<PurityReport[]> getPurityReports(String cookie) {
        try {
            JSONArray jsonResponse = Unirest.get(Constants.URL_BASE + "/api/report/purity").header("Cookie", cookie)
                    .asJson().getBody().getArray();
            PurityReport[] purityReports = new PurityReport[jsonResponse.length()];
            for (int i = 0; i < jsonResponse.length(); i++){
                JSONObject obj = jsonResponse.getJSONObject(i);
                purityReports[i] = new PurityReport(obj.getString("name"), obj.getDouble("lat"),
                        obj.getDouble("long"), obj.getString("condition"), obj.getInt("virus"),
                        obj.getInt("contaminant"), obj.getString("_id"), obj.getString("date"));
            }
            return new Response<>(1, "", purityReports);
        } catch (Exception e){
            e.printStackTrace();
            return new Response<>(0, "", null);
        }
    }
}
