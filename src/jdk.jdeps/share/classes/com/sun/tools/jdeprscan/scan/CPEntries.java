/*
 * Copyright (c) 2016, 2024, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.sun.tools.jdeprscan.scan;

import java.lang.classfile.ClassModel;
import java.lang.classfile.constantpool.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * A container for selected constant pool entries. There are currently
 * lists that contain the following types of CP entries:
 *
 *  - CONSTANT_Class_info
 *  - CONSTANT_Fieldref_info
 *  - CONSTANT_Methodref_info
 *  - CONSTANT_InterfaceMethodref_info
 */
class CPEntries {
    final List<ClassEntry> classes = new ArrayList<>();
    final List<FieldRefEntry> fieldRefs = new ArrayList<>();
    final List<MethodRefEntry> methodRefs = new ArrayList<>();
    final List<InterfaceMethodRefEntry> intfMethodRefs = new ArrayList<>();

    public static CPEntries loadFrom(ClassModel cf) {
        CPEntries entries = new CPEntries();
        for (PoolEntry cpi : cf.constantPool()) {
            switch (cpi) {
                case ClassEntry ce -> entries.classes.add(ce);
                case MethodRefEntry mref -> entries.methodRefs.add(mref);
                case InterfaceMethodRefEntry imref -> entries.intfMethodRefs.add(imref);
                case FieldRefEntry fref -> entries.fieldRefs.add(fref);
                default -> {}
            }
        }
        return entries;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb, Locale.getDefault());
        f.format("Classes:%n");
        f.format("%s%n", classes);
        f.format("FieldRefs:%n");
        f.format("%s%n", fieldRefs);
        f.format("MethodRefs:%n");
        f.format("%s%n", methodRefs);
        f.format("InterfaceMethodRefs:%n");
        f.format("%s%n", intfMethodRefs);
        f.flush();
        return sb.toString();
    }
}
