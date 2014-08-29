/*
 * SonarQube Java
 * Copyright (C) 2012 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.java;

import org.junit.Test;
import org.sonar.java.ast.api.JavaMetric;
import org.sonar.java.ast.visitors.PackageVisitor;
import org.sonar.squidbridge.api.SourceFile;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

public class JavaAstScannerTest {

  @Test
  public void lines_of_code() {
    SourceFile file = JavaAstScanner.scanSingleFile(new File("src/test/files/metrics/LinesOfCode.java"));
    assertThat(file.getInt(JavaMetric.LINES_OF_CODE)).isEqualTo(2);
  }

  @Test
  public void comments() {
    SourceFile file = JavaAstScanner.scanSingleFile(new File("src/test/files/metrics/Comments.java"));
    assertThat(file.getInt(JavaMetric.COMMENT_LINES_WITHOUT_HEADER)).isEqualTo(3);
    assertThat(file.getNoSonarTagLines()).contains(15).hasSize(1);
  }

  @Test
  public void statements() {
    SourceFile file = JavaAstScanner.scanSingleFile(new File("src/test/files/metrics/Statements.java"));
    assertThat(file.getInt(JavaMetric.STATEMENTS)).isEqualTo(16);
  }

  @Test
  public void methods() {
    SourceFile file = JavaAstScanner.scanSingleFile(new File("src/test/files/metrics/Methods.java"));
    assertThat(file.getInt(JavaMetric.METHODS)).isEqualTo(9);
  }

  @Test
  public void parseError() {
    SourceFile file = JavaAstScanner.scanSingleFile(new File("src/test/filesInError/ParseError.java"));
    assertThat(file.getParent().getKey()).isEqualTo(PackageVisitor.UNRESOLVED_PACKAGE);
  }
  @Test
  public void emptyFile() {
    SourceFile file = JavaAstScanner.scanSingleFile(new File("src/test/filesInError/EmptyFile.java"));
    assertThat(file.getParent().getKey()).isEqualTo(PackageVisitor.UNRESOLVED_PACKAGE);
  }

}
