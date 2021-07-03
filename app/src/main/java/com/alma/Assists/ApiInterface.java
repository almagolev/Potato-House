package com.alma.Assists;

import com.alma.Objects.ActionDataModel;
import com.alma.Objects.ClientDataModel;
import com.alma.Objects.PartDataModel;
import com.alma.Objects.ScheduleDataModel;
import com.alma.Objects.WorkoutDataModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("/api/clients")
    Call<List<ClientDataModel>> getAllClients();

    @GET("/api/clients-active/{is_active}")
    Call<List<ClientDataModel>> getAllClientsByStatus(@Path("is_active") boolean is_active);

    @GET("/api/clients-active-gender/{is_active}/{gender}")
    Call<List<ClientDataModel>> getAllClientsByStatus(@Path("is_active") boolean is_active,
                                                      @Path("gender") String gender);

    @GET("/api/clients-b/{birthday}")
    Call<List<ClientDataModel>> getAllClientsBirthday(@Path("birthday") String birthday);

//    @GET("/api/clients-gender/{gender}")
//    Call<List<ClientDataModel>> getAllClientsByGender(@Path("gender") String gender);

    @POST("/api/clients/{id}")
    Call<ClientDataModel> updateClient(@Path("id") int id, @Body ClientDataModel clientDataModel);

    @PUT("/api/clients/")
    Call<ClientDataModel> createClient(@Body ClientDataModel clientDataModel);

//    @GET("/api/schedule")
//    Call<List<ScheduleDataModel>> getAllSchedules();

    @GET("/api/schedule-date/{w_date}")
    Call<List<ScheduleDataModel>> getScheduleByDate(@Path("w_date") String w_date);

    @GET("/api/part/{s_id}")
    Call<List<PartDataModel>> getPartBySID(@Path("s_id") int s_id);

    @DELETE("/api/part/{id}")
    Call<Void> deletePartById(@Path("id") int id);

    @DELETE("/api/schedule/{id}")
    Call<Void> deleteScheduleById(@Path("id") int id);

//    @POST("/api/clients-b/{last_b_wish}/{id}")
//    Call<ClientDataModel> updateBirthdayWish(@Path(value = "last_b_wish") String last_b_wish,
//                                   @Path(value = "id") int clientId);

    @POST("/api/clients-b/")
    Call<ClientDataModel> updateBirthdayWish(@Body ClientDataModel clientDataModel);

    @GET("/api/workouts-active/{is_active}")
    Call<List<WorkoutDataModel>> getAllWorkoutsByStatus(@Path("is_active") boolean is_active);

//    @GET("/api/workouts-active/{is_active}")
//    Call<List<WorkoutDataModel>> getAllWorkoutsByStatus(@Path("is_active") boolean is_active);

    @PUT("/api/schedule")
    Call<ScheduleDataModel> createSchedule(@Body ScheduleDataModel scheduleDataModel);

    @PUT("/api/part")
    Call<PartDataModel> insertPart(@Body PartDataModel partDataModel);

    @PUT("/api/part-u")
    Call<PartDataModel> updatePart(@Body PartDataModel partDataModel);

    @PUT("/api/workouts/")
    Call<WorkoutDataModel> createWorkout(@Body WorkoutDataModel workoutDataModel);

    @GET("/api/actions-by/{sub_do}/{type}")
    Call<List<ActionDataModel>> getAllActionsByChoice(@Path("sub_do") String sub_do, @Path("type") String type);

    @GET("/api/clients-part/{s_id}")
    Call<List<ClientDataModel>> getAllAvailableClients(@Path("s_id") int s_id);
}