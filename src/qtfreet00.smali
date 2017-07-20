.class public Lcom/qtfreet00;
.super Ljava/lang/Object;
.source "qtfreet00.java"


# static fields
.field private static final KEY:Ljava/lang/String; = "qtfreet"

.field private static final hexString:Ljava/lang/String; = "0123456789ABCDEF"


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 7
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static decode(Ljava/lang/String;)Ljava/lang/String;
    .locals 8
    .param p0, "str"    # Ljava/lang/String;

    .prologue
    .line 14
    new-instance v1, Ljava/io/ByteArrayOutputStream;

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v5

    div-int/lit8 v5, v5, 0x2

    invoke-direct {v1, v5}, Ljava/io/ByteArrayOutputStream;-><init>(I)V

    .line 16
    .local v1, "baos":Ljava/io/ByteArrayOutputStream;
    const/4 v2, 0x0

    .local v2, "i":I
    :goto_0
    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v5

    if-ge v2, v5, :cond_0

    .line 17
    const-string v5, "0123456789ABCDEF"

    invoke-virtual {p0, v2}, Ljava/lang/String;->charAt(I)C

    move-result v6

    invoke-virtual {v5, v6}, Ljava/lang/String;->indexOf(I)I

    move-result v5

    shl-int/lit8 v5, v5, 0x4

    const-string v6, "0123456789ABCDEF"

    add-int/lit8 v7, v2, 0x1

    invoke-virtual {p0, v7}, Ljava/lang/String;->charAt(I)C

    move-result v7

    invoke-virtual {v6, v7}, Ljava/lang/String;->indexOf(I)I

    move-result v6

    or-int/2addr v5, v6

    invoke-virtual {v1, v5}, Ljava/io/ByteArrayOutputStream;->write(I)V

    .line 16
    add-int/lit8 v2, v2, 0x2

    goto :goto_0

    .line 18
    :cond_0
    invoke-virtual {v1}, Ljava/io/ByteArrayOutputStream;->toByteArray()[B

    move-result-object v0

    .line 19
    .local v0, "b":[B
    array-length v4, v0

    .line 20
    .local v4, "len":I
    const-string v5, "qtfreet"

    invoke-virtual {v5}, Ljava/lang/String;->length()I

    move-result v3

    .line 21
    .local v3, "keyLen":I
    const/4 v2, 0x0

    :goto_1
    if-ge v2, v4, :cond_1

    .line 22
    aget-byte v5, v0, v2

    const-string v6, "qtfreet"

    rem-int v7, v2, v3

    invoke-virtual {v6, v7}, Ljava/lang/String;->charAt(I)C

    move-result v6

    xor-int/2addr v5, v6

    int-to-byte v5, v5

    aput-byte v5, v0, v2

    .line 21
    add-int/lit8 v2, v2, 0x1

    goto :goto_1

    .line 24
    :cond_1
    new-instance v5, Ljava/lang/String;

    invoke-direct {v5, v0}, Ljava/lang/String;-><init>([B)V

    return-object v5
.end method
