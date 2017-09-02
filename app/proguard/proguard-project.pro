-optimizations !field/*,!class/merging/*,!code/allocation/variable
-optimizationpasses 5

#prevent severe obfuscation
-keep,allowshrinking,allowoptimization class * { <methods>; }

-keepclasseswithmembernames,allowshrinking,allowoptimization class * {
    native <methods>;
}

-keepclasseswithmembers,allowshrinking,allowoptimization class * {
	public <init>(android.content.Context);
    public <init>(android.content.Context,android.util.AttributeSet);
    public <init>(android.content.Context,android.util.AttributeSet,int);
}

-keepclassmembers,allowoptimization class * {
    public <init>(android.content.Context);
    public <init>(android.content.Context,android.util.AttributeSet);
    public <init>(android.content.Context,android.util.AttributeSet,int);
}

-renamesourcefileattribute SourceFile
-keepattributes SourceFile, LineNumberTable, Signature, Exceptions, *Annotation*
