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
    .add(PrintText("1234********9456", fontStyle = FontStyle.BOLD))
    .add(PrintText("Jumadi Janjaya", fontStyle = FontStyle.BOLD))
    .add(PrintText("SETTLED", fontStyle = FontStyle.BOLD))
    .feed()
    .add(LayoutAbsolute(childs = arrayListOf(
        PrintText("REF NO: 123456789012"),
        PrintText("MAPPR: 123456", align = Align.RIGHT),
    )))
    .add(LayoutAbsolute(childs = arrayListOf(
        PrintText("BATCH  : 123456789012"),
        PrintText("INVOICE: 123456", align = Align.RIGHT),
    )))
    .add(PrintText("DATETIME: 22 FEB 23/01:15:18(GMT+07:00)"))
    .feed(3.feed())
    .add(LayoutAbsolute(childs = arrayListOf(
        PrintTextFont(font(R.font.mynerve_regular),"TOTAL  :", FontSize.LARGE, fontStyle = FontStyle.BOLD),
        PrintTextFont(font(R.font.mynerve_regular),"RP. 200.000", FontSize.LARGE, align = Align.RIGHT, fontStyle = FontStyle.BOLD),
    )))
    .add(PrintText("TC:  123456789123"))
    .feed(2.feed())
    .add(PrintText("NO SIGNATURE REQUESTED", align = Align.CENTER))
    .add(PrintText("*** PIN VERIFICATION SUCCESS ***", align = Align.CENTER))
    .feed().addFooter().build()
```

#### Simple 2

```kotlin
Print().addHeader(logo)
    .add(PrintText("STATUS", FontSize.LARGE.size, fontStyle = FontStyle.BOLD))
    .add(LayoutFlex(childs = arrayListOf(
        PrintText("Customer"), PrintText(":"),
        PrintText("Jumadi Janjaya", align = Align.RIGHT),
    ), flexs = flex3))
    .add(LayoutFlex(childs = arrayListOf(
        PrintText("TXID"), PrintText(":"),
        PrintText("123456789012346", align = Align.RIGHT),
    ), flexs = flex3))
    .add(LayoutFlex(childs = arrayListOf(
        PrintText("Date"), PrintText(":"),
        PrintText("18 Des 2022", align = Align.RIGHT),
    ), flexs = flex3))
    .add(LayoutFlex(childs = arrayListOf(
        PrintText("Time"), PrintText(":"),
        PrintText("14:15:16 (GMT +07:00)", align = Align.RIGHT),
    ), flexs = flex3))
    .feed(2.feed())
    .add(LayoutAbsolute(childs = arrayListOf(
        PrintText("TOTAL", FontSize.LARGE.size, fontStyle = FontStyle.BOLD),
        PrintText("RP. 200.000", FontSize.LARGE.size, align = Align.RIGHT, fontStyle = FontStyle.BOLD),
    ))).feed(2.feed())
    .add(LayoutFlex(childs = arrayListOf(
        PrintText("PAID BY"), PrintText(":"),
        PrintText("Gopay", align = Align.RIGHT),
    ), flexs = flex3))
    .add(LayoutFlex(childs = arrayListOf(
        PrintText("VIA"), PrintText(":"),
        PrintText("Cashlez Link", align = Align.RIGHT),
    ), flexs = flex3))
    .add(LayoutFlex(childs = arrayListOf(
        PrintText("TID"), PrintText(":"),
        PrintText("12345678", align = Align.RIGHT),
    ), flexs = flex3))
    .add(LayoutFlex(childs = arrayListOf(
        PrintText("MID"), PrintText(":"),
        PrintText("123456789012", align = Align.RIGHT),
    ), flexs = flex3))
    .add(LayoutFlex(childs = arrayListOf(
        PrintText("REF.NO"), PrintText(":"),
        PrintText("12345678901267", align = Align.RIGHT),
    ), flexs = flex3))
    .feed(2.feed())
    .addFooter().build()
```
