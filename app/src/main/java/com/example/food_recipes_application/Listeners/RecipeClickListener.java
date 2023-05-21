package com.example.food_recipes_application.Listeners;

public interface RecipeClickListener {

    void onRecipeClicked(String id);
/* TODO: IN SEARCH OUTPUT ACTIVITY
    private interface CallRecipeDetails{
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
        @Path("id") int id;
        @Query(apiKey) String apiKey
        );
    }
    public void getRecipeDetails(RecipeDetailsListener listener, int id){
    CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class)
    Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id, context.getString(R.string.api_key));
    call.enqueue(new Callback<RecipeDetailsResponse>() {
    @Override
    public void onResponse(Call<RecipeDetailsResponse> call, Response<Re ){
    if(!response.isSuccessful()){
    listener.didError(response.message());
    return;
    }
    listener.didFetch(response.body(), response.message());
    }
    @Override
    public void onFailure(Call<RecipeDetailsResponse> call, Throwable){
    listener.didError(t.getMessage());
*/
}