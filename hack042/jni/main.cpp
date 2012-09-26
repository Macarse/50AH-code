#-------------------------------------------------------------------------------
# Copyright (c) 2012 Manning
# See the file license.txt for copying permission.
#-------------------------------------------------------------------------------
#include <jni.h>
#include <sqlite3.h>
#include <assert.h>
#include <android/log.h>
#include <math.h>

#ifndef LOG_TAG
#define LOG_TAG "hack042-native"
#endif

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__))

extern "C" {

#define DEG2RAD(degrees) (degrees * 0.01745327) // degrees * pi over 180

static void distanceFunc(sqlite3_context *context, int argc, sqlite3_value **argv) {
	// check that we have four arguments (lat1, lon1, lat2, lon2)
	assert(argc == 4);
	// check that all four arguments are non-null
	if (sqlite3_value_type(argv[0]) == SQLITE_NULL ||
	 sqlite3_value_type(argv[1]) == SQLITE_NULL ||
	  sqlite3_value_type(argv[2]) == SQLITE_NULL ||
	   sqlite3_value_type(argv[3]) == SQLITE_NULL) {

		sqlite3_result_null(context);
		return;
	}

	// get the four argument values
	double lat1 = sqlite3_value_double(argv[0]);
	double lon1 = sqlite3_value_double(argv[1]);
	double lat2 = sqlite3_value_double(argv[2]);
	double lon2 = sqlite3_value_double(argv[3]);
	// convert lat1 and lat2 into radians now, to avoid doing it twice below
	double lat1rad = DEG2RAD(lat1);
	double lat2rad = DEG2RAD(lat2);
	// apply the spherical law of cosines to our latitudes and longitudes, and set the result appropriately
	// 6378.1 is the approximate radius of the earth in kilometres
	sqlite3_result_double(context, acos(sin(lat1rad) * sin(lat2rad) + cos(lat1rad) * cos(lat2rad) * cos(DEG2RAD(lon2) - DEG2RAD(lon1))) * 6378.1);
}

jobject Java_com_manning_androidhacks_hack042_db_DatabaseHelper_getNear(JNIEnv *env, jobject thiz,
	jstring dbPath, jfloat lat, jfloat lon) {

	sqlite3 *db;
	sqlite3_stmt *stmt;
	const char *path = env->GetStringUTFChars(dbPath, 0);

	jclass arrayClass = env->FindClass("java/util/ArrayList");
	jmethodID mid_init =  env->GetMethodID(arrayClass, "<init>", "()V");
	jobject objArr = env->NewObject(arrayClass, mid_init);
	jmethodID mid_add = env->GetMethodID(arrayClass, "add", "(Ljava/lang/Object;)Z");

	jclass poiClass = env->FindClass("com.manning.androidhacks.hack042.model.Poi");
	jmethodID poi_mid_init =  env->GetMethodID(poiClass, "<init>", "(Ljava/lang/String;FFF)V");

	sqlite3_open(path, &db);
	env->ReleaseStringUTFChars(dbPath, path);

	sqlite3_create_function(db, "distance", 4, SQLITE_UTF8, NULL, &distanceFunc, NULL, NULL);

	if (sqlite3_prepare(db,
	 "SELECT title, latitude, longitude, distance(latitude, longitude, ?, ?) as kms FROM pois ORDER BY kms",
	 -1, &stmt, NULL) == SQLITE_OK) {
		int err;
		sqlite3_bind_double(stmt, 1, lat);
		sqlite3_bind_double(stmt, 2, lon);

		while ((err = sqlite3_step(stmt)) == SQLITE_ROW) {
			const char *name = (char const *)sqlite3_column_text(stmt, 0);
			jfloat latitude = sqlite3_column_double(stmt, 1);
			jfloat longitude = sqlite3_column_double(stmt, 2);
			jfloat distance = sqlite3_column_double(stmt, 3);

			jobject poiObj = env->NewObject(poiClass, poi_mid_init,
				env->NewStringUTF(name),
				latitude,
				longitude,
				distance);

			env->CallBooleanMethod(objArr, mid_add, poiObj);
		}

		if (err != SQLITE_DONE) {
			LOGI("Query execution failed: %s\n", sqlite3_errmsg(db));
		}

		sqlite3_finalize(stmt);

	} else {
		LOGI("Can't execute query: %s\n", sqlite3_errmsg(db));
	}

  return objArr;
}


}
