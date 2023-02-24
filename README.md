# PrintBitmap

### Installing

Add repository in settings.gradle 
```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```
And add dependencies

```gradle
dependencies {
    implementation 'com.github.jumadi59:PrintBitmap:x.x.x'
}
```

#### Sample 1

```kotlin
Print().addHeader(logo)
            .add(
                LayoutAbsolute(childs = arrayListOf(
                PrintText("TID: 123456"),
                PrintText("MID: 123456789012", align = Align.RIGHT),
            ))
            )
            .add(PrintText("CARD TYPE: OFF-US GPN (DIP)"))
            .add(PrintText("1234********9456", isBold = true))
            .add(PrintText("Jumadi Janjaya", isBold = true))
            .add(PrintText("SETTLED", isBold = true))
            .feed()
            .add(
                LayoutAbsolute(childs = arrayListOf(
                PrintText("REF NO: 123456789012"),
                PrintText("MAPPR: 123456", align = Align.RIGHT),
            ))
            )
            .add(
                LayoutAbsolute(childs = arrayListOf(
                PrintText("BATCH  : 123456789012"),
                PrintText("INVOICE: 123456", align = Align.RIGHT),
            ))
            )
            .add(PrintText("DATETIME: 22 FEB 23/01:15:18(GMT+07:00)"))
            .feed(3.feed())
            .add(
                LayoutAbsolute(childs = arrayListOf(
                PrintText("TOTAL  :", FontSize.LARGE.size, isBold = true),
                PrintText("RP. 200.000", FontSize.LARGE.size, align = Align.RIGHT, isBold = true),
            ))
            )
            .add(PrintText("TC:  123456789123"))
            .feed(2.feed())
            .add(PrintText("NO SIGNATURE REQUESTED", align = Align.CENTER))
            .add(PrintText("*** PIN VERIFICATION SUCCESS ***", align = Align.CENTER))
            .feed().addFooter().build()
```

#### Simple 2

```kotlin
Print().addHeader(logo)
            .add(LayoutAbsolute(childs = arrayListOf(
                PrintText("TID: 123456"),
                PrintText("MID: 123456789012", align = Align.RIGHT),
            )))
            .add(PrintText("CARD TYPE: VISA (DIP)"))
            .add(PrintText("1234********9456", isBold = true))
            .add(PrintText("Jumadi Janjaya"))
            .add(PrintText("SALE", isBold = true))
            .feed()
            .add(PrintText("EXP     : ***/***"))
            .add(LayoutAbsolute(childs = arrayListOf(
                PrintText("BATCH  : 123456"),
                PrintText("TRACE : 123456", align = Align.RIGHT),
            )))
            .add(LayoutAbsolute(childs = arrayListOf(
                PrintText("RREF#   : 123456"),
                PrintText("APPR. : 123456", align = Align.RIGHT),
            )))
            .add(PrintText("DATETIME: 22 FEB 2023/01:15:18(GMT+07:00)"))
            .feed(3.feed())
            .add(LayoutFlex(childs = arrayListOf(PrintText("APPL ID"), PrintText(":"), PrintText("A0000000123")),
                flexs = floatArrayOf(0.3F, 0.1f, 0.6F)))
            .add(LayoutFlex(childs = arrayListOf(PrintText("APP NAME"), PrintText(":"), PrintText("JCB")),
                flexs = floatArrayOf(0.3F, 0.1f, 0.6F)))
            .add(LayoutFlex(childs = arrayListOf(PrintText("APP CRYPT"), PrintText(":"), PrintText("A1A10000001A")),
                flexs = floatArrayOf(0.3F, 0.1f, 0.6F)))
            .add(LayoutFlex(childs = arrayListOf(PrintText("TVR VALUE"), PrintText(":"), PrintText("23000089")),
                flexs = floatArrayOf(0.3F, 0.1f, 0.6F)))
            .feed(3.feed())
            .add(LayoutAbsolute(childs = arrayListOf(
                PrintText("TOTAL", FontSize.LARGE.size, isBold = true),
                PrintText("RP. 200.000", FontSize.LARGE.size, align = Align.RIGHT, isBold = true),
            )))
            .add(PrintText("TC:  123456789123"))
            .feed(2.feed())
            .add(PrintText("NO SIGNATURE REQUESTED", align = Align.CENTER))
            .add(PrintText("*** PIN VERIFICATION SUCCESS ***", align = Align.CENTER))
            .feed().addFooter().build()
```
