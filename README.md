# CommonView
About some common view in project, just sort out them for reuse.

Using ButtonTextView,it can be used as a button in the project ,you can setting the normal_color,press_color,corner_size 
and other TextView attributes.
For example:
<com.view.shj.commonview.ButtonTextView
        android:id="@+id/btnTv"
        style="@style/white_sp12"
        android:text="text"
        android:padding="8dp"
        app:corner_size="8"
        app:normal_color="@color/light_green"
        app:press_color="@color/green"
        android:layout_margin="10dp"/>
..
