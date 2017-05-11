package com.fh.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 正则表达式代码
 * <table border="1">
 * <tr><td>功能</td><td colspan="4">验证身份证正则表达式</td></tr>
 * <tr><td>注意事项</td><td colspan="4">无</td></tr>
 * <tr><td rowspan="2">版本历史</td>
 * <td>修改人</td><td>日期</td><td>版本</td><td>描述</td></tr>
 * <tr><td>Administrator</td><td>2016年12月13日 上午11:37:45
 * </td><td>1.0.1</td><td>创建</td></tr>
 * </table>
 */
public class Regex {

	public static void main(String[] args) {
        testID_Card();
    }

    public static void testID_Card() {
        // 测试是否为合法的身份证号码
        String[] strs = { "371581198609080036", "13068119931214201x",
                "130681199812152019", "130503670401001", "12345678901234x",
                "1234567890123" };
        // 准备正则表达式（身份证有15位和18位两种，身份证的最后一位可能是字母）
        String regex = "(\\d{14}\\w)|\\d{17}\\w";
        // 准备开始匹配，判断所有的输入是否是正确的
        Pattern regular = Pattern.compile(regex); // 创建匹配的规则Patter

        StringBuilder sb = new StringBuilder();
        // 遍历所有要匹配的字符串
        for (int i = 0; i < strs.length; i++) {

            Matcher matcher = regular.matcher(strs[i]);// 创建一个Matcher
            sb.append("身份证:  ");
            sb.append(strs[i]);
            sb.append("   匹配:");
            sb.append(matcher.matches());
            System.out.println(sb.toString());
            sb.delete(0, sb.length());// 清空StringBuilder的方法
        }

        GetBirthDay(strs);

    }

    private static void GetBirthDay(String[] strs) {
        System.out.println("准备开始获取出生日期");
        // 准备验证规则
        Pattern BirthDayRegular = Pattern.compile("(\\d{6})(\\d{8})(.*)");
        // .*连在一起就意味着任意数量的不包含换行的字符
        Pattern YearMonthDayRegular = Pattern
                .compile("(\\d{4})(\\d{2})(\\d{2})");
        for (int i = 0; i < strs.length; i++) {
            Matcher matcher = BirthDayRegular.matcher(strs[i]);

            if (matcher.matches()) {
                Matcher matcher2 = YearMonthDayRegular
                        .matcher(matcher.group(2));
                if (matcher2.matches()) {
                    System.out.println("身份证号码:"+strs[i]+"    中的出生年月分解为： "+ matcher2.group(1) +"年" 
                            + matcher2.group(2) + "月" + matcher2.group(3)+ "日");

                }
            }
        }

    }
}
