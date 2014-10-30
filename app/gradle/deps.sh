#!/bin/sh

classpath="$1"
classes=""
for class in $2 $3
do
    classes="$classes $classpath$class"
done
#echo "class $classes"
#classes="$classpath/circlebinder/playground/PlayGroundActivity.class $classpath/circlebinder/common/app/CircleBinderApplication.class"

jdeps=/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/bin/jdeps

"$jdeps" -recursive -verbose \
    -cp "$classpath" \
    -regex "net.ichigotake.*|circlebinder.*" \
    $classes \
    | perl -le 'while(<>){ print (m/-> (.+?)\s/) }' \
    | grep -v '\$' \
    | sort | uniq
