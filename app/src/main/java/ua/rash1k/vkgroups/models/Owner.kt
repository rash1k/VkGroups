package ua.rash1k.vkgroups.models

import com.vk.sdk.api.model.Identifiable

//Класс для вывода header
interface Owner : Identifiable {

    fun getFullName(): String
    fun getPhoto(): String?
}