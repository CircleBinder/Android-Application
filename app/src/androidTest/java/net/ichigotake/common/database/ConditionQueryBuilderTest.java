package net.ichigotake.common.database;

import android.test.AndroidTestCase;

public final class ConditionQueryBuilderTest extends AndroidTestCase {

    public void test指定無し() {
        ConditionQueryBuilder builder = new ConditionQueryBuilder();
        assertEquals("", builder.getQuery());
        assertEquals(0, builder.getArguments().length);
    }

    public void test1つの条件式を生成() {
        ConditionQueryBuilder builder = new ConditionQueryBuilder();
        String condition = "id = 1";
        builder.and(condition);

        assertEquals("(" + condition + ")", builder.getQuery());
        assertEquals(0, builder.getArguments().length);
    }

    public void testプレースホルダを1つ利用する() {
        ConditionQueryBuilder builder = new ConditionQueryBuilder();
        String condition = "id = ?";
        int argument = 1;
        builder.and(condition, argument);

        assertEquals("(" + condition + ")", builder.getQuery());
        assertEquals(argument, builder.getArguments()[0]);
    }

    public void test条件式を複数指定する() {
        ConditionQueryBuilder builder = new ConditionQueryBuilder();
        String idCondition = "id = ?";
        int idArgument = 1;
        builder.and(idCondition, idArgument);
        String nameCondition = "name like ?";
        String nameArgument = "%taro%";
        builder.and(nameCondition, nameArgument);
        String ageCondition = "age = ?";
        String ageArgument = "17";
        builder.or(ageCondition, ageArgument);

        String expectedQuery = "(" + idCondition + ")" +
                " AND (" + nameCondition + ")" +
                " OR (" + ageCondition + ")";
        assertEquals(expectedQuery, builder.getQuery());
        assertEquals(idArgument, builder.getArguments()[0]);
        assertEquals(nameArgument, builder.getArguments()[1]);
        assertEquals(ageArgument, builder.getArguments()[2]);
    }

    public void test条件式を入れ子で指定する() {
        ConditionQueryBuilder builder = new ConditionQueryBuilder();
        String idCondition = "id = ?";
        ConditionQueryBuilder nestedQueryBuilder = new ConditionQueryBuilder();
        int idArgument = 1;
        builder.and(idCondition, idArgument);
        String nameCondition = "name like ?";
        String nameArgument = "%taro%";
        nestedQueryBuilder.and(nameCondition, nameArgument);
        String ageCondition = "age = ?";
        String ageArgument = "17";
        nestedQueryBuilder.or(ageCondition, ageArgument);
        builder.and(nestedQueryBuilder);

        String expectedQuery = "(" + idCondition + ")" +
                " AND ((" + nameCondition + ") OR (" + ageCondition + "))";
        assertEquals(expectedQuery, builder.getQuery());
        assertEquals(idArgument, builder.getArguments()[0]);
        assertEquals(nameArgument, builder.getArguments()[1]);
        assertEquals(ageArgument, builder.getArguments()[2]);
    }

    public void testIsEmpty() {
        ConditionQueryBuilder builder = new ConditionQueryBuilder();

        assertTrue(builder.isEmpty());

        String condition = "id = ?";
        int argument = 1;
        builder.and(condition, argument);

        assertFalse(builder.isEmpty());
    }

    public void testDslLikeCall() {
        String idCondition = "id = ?";
        int idArgument = 1;
        String nameCondition = "name = ?";
        String nameArgument = "taro";
        ConditionQueryBuilder builder = ConditionQueryBuilder
                .where(idCondition, idArgument)
                .and(nameCondition, nameArgument);

        String expectedQuery = "(" + idCondition + ") AND (" + nameCondition + ")";
        assertEquals(expectedQuery, builder.getQuery());
        assertEquals(idArgument, builder.getArguments()[0]);
        assertEquals(nameArgument, builder.getArguments()[1]);
    }

}
