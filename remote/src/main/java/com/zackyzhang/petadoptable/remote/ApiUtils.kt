package com.zackyzhang.petadoptable.remote

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser

object ApiUtils {
    fun cleanInvalidSymbol(json: String): String {
        return json
                .replace("\$t", "value")
                .replace("@encoding", "encoding")
                .replace("@version", "version")
                .replace("@size", "size")
                .replace("@id", "id")
                .replace("@xmlns:xsi", "xmlns_xsi")
                .replace("@xsi:noNamespaceSchemaLocation", "xsi_noNamespaceSchemaLocation")
    }

    fun fixBreedObject(json: String): String {
        val jsonParser = JsonParser()
        val jsonElements = jsonParser.parse(json)
        val jsonObj = jsonElements.asJsonObject

        val petFinder = jsonObj!!.getAsJsonObject("petfinder")
        if (petFinder.has("pets")) {
            val pets = petFinder.getAsJsonObject("pets").getAsJsonArray("pet")
            for (i in 0 until pets.size()) {
                val pet = pets.get(i)
                val breeds = pet.asJsonObject.getAsJsonObject("breeds")
                val breed = breeds.get("breed")
                if (breed is JsonObject) {
                    val jsonArray = JsonArray()
                    jsonArray.add(breed)
                    breeds.add("breed", jsonArray)
                } else {
                    // no-op
                }
            }
        } else if (petFinder.has("pet")) {
            val pet = petFinder.getAsJsonObject("pet")
            val breeds = pet.getAsJsonObject("breeds")
            val breed = breeds.get("breed")
            if (breed is JsonObject) {
                val jsonArray = JsonArray()
                jsonArray.add(breed)
                breeds.add("breed", jsonArray)
            } else {
                // no-op
            }
        }
        return jsonObj.toString()
    }
}