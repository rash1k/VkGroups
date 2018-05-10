package ua.rash1k.vkgroups.di.module

import android.app.Application
import android.graphics.Typeface
import dagger.Module
import dagger.Provides
import ua.rash1k.vkgroups.common.utils.UiHelper
import javax.inject.Singleton


//Module ответственны за инициализацию и предоставление необходимых нам зависимостей такие как
// Context, managers, а также любые классы ссылки на которые мы хотим получить
//Посути это то откуда берутся зависимости

//Components нужны для Inject мест классов, активностей,
// фрагментов, где мы хоти получить ссылки на зывисимости

//Отвечает за предоставление контекста
//Аннотация нужна для пометки класса и формирования графа зависимости
@Module
class AppModule(private val mApplication: Application) {


    @Singleton//Объект будет в одном экземпляре
    @Provides //Что этот метод предоставляет нужный нам объект для внедрения зависимостей
    //которую мы можем Inject в нужном месте
    fun provideContext(): Application {
        return mApplication
    }


    //Нужен для того чтобы TextView понимал какой тип шрифта ему использовать
    //Наш текст для счетчиков будет преобразован в иконки
    @Singleton
    @Provides
    fun provideGoogleTypeface(context: Application): Typeface {
        return Typeface.createFromAsset(context.assets, "MaterialIcons-Regular.ttf")
    }

    @Singleton
    @Provides
    fun provideUiHelper(): UiHelper {
        return UiHelper()
    }
}