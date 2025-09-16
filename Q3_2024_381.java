// Q3_2024_381.java — total בלבד או יחד עם minTime (אין אפסים בדוגמאות)

public class Q3_2024_381 {

  // סעיף א
  public static int total(int[] arr, int numSeconds) {
    if (arr == null || numSeconds <= 0) return 0;
    int count = 0;
    for (int t : arr) {
      if (t > 0) count += numSeconds / t;  // חלוקה שלמה
    }
    return count;
  }

  // סעיף ב (אם צריך)
  public static int minTime(int[] arr, int amount) {
    if (amount <= 0) return 0;
    int sec = 1;
    int numScrew = total(arr, sec);
    while (numScrew < amount) {
      sec++;
      numScrew = total(arr, sec);
    }
    return sec;
  }

  /* טסטים בלי אפסים במערך */
  private static void assertEq(String name, int got, int exp) {
    System.out.println((got == exp ? "PASS" : "FAIL") +
        " — " + name + "  got=" + got + " expected=" + exp);
  }

  public static void main(String[] args) {
    System.out.println("== total ==");
    // 6/1 + 6/9 + 6/3 = 6 + 0 + 2 = 8
    assertEq("A1", total(new int[]{1, 9, 3}, 6), 8);
    // 5/3 + 5/1 + 5/2 = 1 + 5 + 2 = 8
    assertEq("A2", total(new int[]{3, 1, 2}, 5), 8);
    // 7/2 = 3
    assertEq("A3", total(new int[]{2}, 7), 3);

    System.out.println("\n== minTime ==");
    // ב-3 שניות: 1+3+1=5
    assertEq("B1", minTime(new int[]{3, 1, 2}, 5), 3);
    // ב-6 שניות: 2+6+3=11 ≥ 9; ב-5 זה 8
    assertEq("B2", minTime(new int[]{3, 1, 2}, 9), 6);
    // מכונה אחת t=5, amount=7 → 35 שניות
    assertEq("B3", minTime(new int[]{5}, 7), 35);
  }
}
