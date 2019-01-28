package com.zzw.trta.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @author zhaozhiwei
 * @date 2018/11/23 21:18
 */
public class ScriptManagerTest {

    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByName("js");
        engine.eval("print(1111)");
    }
}
