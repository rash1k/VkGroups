package ua.rash1k.vkgroups.models.attachment

import com.vk.sdk.api.model.Identifiable

//В нём будут сожержаться поля для каждого типа attachment
interface Attachment : Identifiable {
    fun getType():String
}