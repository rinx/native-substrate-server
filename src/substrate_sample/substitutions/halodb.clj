(ns substrate-sample.substitutions.halodb
  (:import [com.oracle.svm.core.annotate Substitute TargetClass]
           #_[com.oath.halodb NativeMemoryAllocator]))

(defprotocol NativeMemoryAllocator
  (allocate [this sizea])
  (free [this peer])
  (getTotalAllocated [this]))

(deftype ^{TargetClass {:targetClass "com.oath.halodb.JNANativeAllocator"}
           Substitute true}
  JNANativeAllocatorSubstitution []
  NativeMemoryAllocator
  (^{Substitute true
     Override true}
    allocate [this size]
    0)
  (^{Substitute true
     Override true}
    free [this peer]
    nil)
  (^{Substitute true
     Override true}
    getTotalAllocated [this]
    0))
