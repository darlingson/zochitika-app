package com.codeshinobi.zochitika.api

import android.net.http.HttpResponseCache.install

val supabase = createSupabaseClient(supabaseUrl, supabaseKey) {
    //Already the default serializer, but you can provide a custom Json instance (optional):
    defaultSerializer = KotlinXSerializer(Json {
        //apply your custom config
    })
}
