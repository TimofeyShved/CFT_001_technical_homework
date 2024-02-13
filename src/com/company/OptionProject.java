package com.company;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OptionProject {

    private String o="";
    private String p="";
    private Boolean s=false;
    private Boolean f=false;
    private Boolean a=false;

    OptionProject(String option){
        Pattern patternFillter1 = Pattern.compile("-.?[^\\-]*");
        Matcher matcherFillter1 = patternFillter1.matcher(option);
        while (matcherFillter1.find()){
            String optionValue = matcherFillter1.group();
            switch (optionValue.charAt(1)) {
                case 'o':
                    this.o = optionValue.substring(3).replaceAll("\\s","");;
                    break;
                case 'p':
                    this.p = optionValue.substring(3).replaceAll("\\s","");;
                    break;
                case 's':
                    this.s=true;
                    break;
                case 'f':
                    this.f=true;
                    break;
                case 'a':
                    this.f=true;
                    break;
                default:
                    System.out.println(optionValue.charAt(1) +" no exists!");
                    break;
            }
        }
    }

    public void setO(String o) {
        this.o = o;
    }

    public void setP(String p) {
        this.p = p;
    }

    public void setS(Boolean s) {
        this.s = s;
    }

    public void setF(Boolean f) {
        this.f = f;
    }

    public void setA(Boolean a) {
        this.a = a;
    }

    public String getO() {
        return o;
    }

    public String getP() {
        return p;
    }

    public Boolean getS() {
        return s;
    }

    public Boolean getF() {
        return f;
    }

    public Boolean getA() {
        return a;
    }
}
