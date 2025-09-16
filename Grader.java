// Grader.java — מריץ את פתרונות Q3_2024_381 וכותב JSON ל-stdout (לשימוש ע"י run.sh)
import java.util.*;
import java.time.*;

class Grader {

  static class ACase { int[] arr; int numSeconds; int expected;
    ACase(int[] a, int n, int e){arr=a; numSeconds=n; expected=e;} }
  static class BCase { int[] arr; int amount; int expected;
    BCase(int[] a, int m, int e){arr=a; amount=m; expected=e;} }

  static String toJsonIntArray(int[] a){
    StringBuilder sb=new StringBuilder("[");
    for(int i=0;i<a.length;i++){ if(i>0) sb.append(','); sb.append(a[i]); }
    sb.append(']');
    return sb.toString();
  }
  static void appendEscaped(StringBuilder sb,String s){
    if(s==null){ sb.append("null"); return; }
    sb.append('"');
    for(char c: s.toCharArray()){
      switch(c){
        case '\\': sb.append("\\\\"); break;
        case '\"': sb.append("\\\""); break;
        case '\n': sb.append("\\n"); break;
        case '\r': sb.append("\\r"); break;
        case '\t': sb.append("\\t"); break;
        default: sb.append(c);
      }
    }
    sb.append('"');
  }

  public static void main(String[] args){
    String qid = "Q3_2024_381";
    String part = (args.length>0 ? args[0] : "ALL").toUpperCase(Locale.ROOT);
    if(!part.equals("A") && !part.equals("B")) part = "ALL";

    List<String> runs = new ArrayList<>();
    long t0 = System.nanoTime();

    // --- סעיף א: total ---
    if(!part.equals("B")){
      ACase[] A = new ACase[]{
        new ACase(new int[]{1,9,3}, 6, 8),   // A1
        new ACase(new int[]{3,1,2}, 5, 8),   // A2
        new ACase(new int[]{2},      7, 3),  // A3
      };
      for(int i=0;i<A.length;i++){
        ACase c = A[i];
        long s = System.nanoTime();
        String status; Integer got=null; String err=null;
        try {
          got = Q3_2024_381.total(c.arr, c.numSeconds);
          status = (got==c.expected) ? "PASS" : "FAIL";
        } catch(Throwable e){ status="EXCEPTION"; err=e.toString(); }
        long ms = (System.nanoTime()-s)/1_000_000;

        StringBuilder r = new StringBuilder();
        r.append("{\"caseIndex\":").append(i)
         .append(",\"fn\":\"total\"")
         .append(",\"status\":\"").append(status).append("\"")
         .append(",\"input\":{\"arr\":").append(toJsonIntArray(c.arr))
         .append(",\"numSeconds\":").append(c.numSeconds).append("}")
         .append(",\"expected\":").append(c.expected)
         .append(",\"got\":").append(got==null? "null" : got.toString())
         .append(",\"durationMs\":").append(ms);
        if(err!=null){ r.append(",\"error\":"); appendEscaped(r,err); }
        r.append("}");
        runs.add(r.toString());
      }
    }

    // --- סעיף ב: minTime ---
    if(!part.equals("A")){
      BCase[] B = new BCase[]{
        new BCase(new int[]{3,1,2}, 5, 3),    // B1
        new BCase(new int[]{3,1,2}, 9, 6),    // B2
        new BCase(new int[]{5},      7, 35),  // B3
      };
      for(int i=0;i<B.length;i++){
        BCase c = B[i];
        long s = System.nanoTime();
        String status; Integer got=null; String err=null;
        try {
          got = Q3_2024_381.minTime(c.arr, c.amount);
          status = (got==c.expected) ? "PASS" : "FAIL";
        } catch(Throwable e){ status="EXCEPTION"; err=e.toString(); }
        long ms = (System.nanoTime()-s)/1_000_000;

        StringBuilder r = new StringBuilder();
        r.append("{\"caseIndex\":").append(i)
         .append(",\"fn\":\"minTime\"")
         .append(",\"status\":\"").append(status).append("\"")
         .append(",\"input\":{\"arr\":").append(toJsonIntArray(c.arr))
         .append(",\"amount\":").append(c.amount).append("}")
         .append(",\"expected\":").append(c.expected)
         .append(",\"got\":").append(got==null? "null" : got.toString())
         .append(",\"durationMs\":").append(ms);
        if(err!=null){ r.append(",\"error\":"); appendEscaped(r,err); }
        r.append("}");
        runs.add(r.toString());
      }
    }

    long totalMs = (System.nanoTime()-t0)/1_000_000;
    boolean passAll = true;
    for(String r : runs){
      if(r.contains("\"status\":\"FAIL\"") || r.contains("\"status\":\"EXCEPTION\"")){
        passAll = false; break;
      }
    }

    StringBuilder out = new StringBuilder();
    out.append("{\"qid\":\"").append(qid).append("\"")
       .append(",\"part\":\"").append(part).append("\"")
       .append(",\"passAll\":").append(passAll)
       .append(",\"totalMs\":").append(totalMs)
       .append(",\"savedAt\":"); appendEscaped(out, java.time.Instant.now().toString());
    out.append(",\"runs\":[");
    for(int i=0;i<runs.size();i++){ if(i>0) out.append(','); out.append(runs.get(i)); }
    out.append("]}");
    System.out.println(out.toString());
  }
}
