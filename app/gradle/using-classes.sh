#!/bin/sh

buildDir="$1"
classpath="$buildDir/intermediates/classes/playground/debug"
classes="$classpath/circlebinder/playground/PlayGroundActivity.class $classpath/circlebinder/common/app/CircleBinderApplication.class"
jdeps=/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/bin/jdeps

"$jdeps" -recursive -verbose \
    -cp "$classpath" \
    -regex "net.ichigotake.*|circlebinder.*" \
    $classes \
    | perl -le 'while(<>){ print (m/-> (.+?)\s/) }' \
    | sort | uniq


