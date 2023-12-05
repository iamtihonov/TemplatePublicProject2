#
# У каждой группы правил, должна быть указана библиотека для который используются правила,
# протестированная версия библиотеки из файла build.gradle.kts и в скобочках источник правил.
# Автоматически подтянутые правила расположены по пути:
# app-> build-> intermediates-> exploded-aar и ища proguard.txt определение.
# Иногда что бы сборка собралась, достаточно выполнить Build -> Rebuild Project
#

#Эти 3 флага позволят Proguard изменять модификаторы доступа классов и членов класса,
#переупаковывать все классы и удалять структуру вашего пакета и позволять вам достичь этого:
-repackageclasses
-allowaccessmodification

# Убираем вывод сообщений в лог в релизной зборке. Просмотр крашей делаем через Crashlistics
# Метод Timber.e() не удаляем, так как он используется Crashlistics
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static *** w(...);
    public static *** e(...);
}
-assumenosideeffects class timber.log.Timber* {
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static *** w(...);
}

#Retrofit 2.5.0 (https://github.com/square/retrofit#proguard)
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Keep annotation default values (e.g., retrofit2.http.Field.encoded).
-keepattributes AnnotationDefault

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# Keep inherited services.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface * extends <1>

# With R8 full mode generic signatures are stripped for classes that are not
# kept. Suspend functions are wrapped in continuations where the type argument
# is used.
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# R8 full mode strips generic signatures from return types if not kept.
-if interface * { @retrofit2.http.* public *** *(...); }
-keep,allowoptimization,allowshrinking,allowobfuscation class <3>

# With R8 full mode generic signatures are stripped for classes that are not kept.
-keep,allowobfuscation,allowshrinking class retrofit2.Response

#Dagger v2 2.2.1 (правила писал сам)
-dontwarn com.google.errorprone.annotations.**

# Добавил после неудачной сблорки и совета ProGuard
-dontwarn kotlinx.serialization.KSerializer
-dontwarn kotlinx.serialization.Serializable