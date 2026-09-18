package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// TODO ここに追加
		//		URLを取得
		webDriver.get("http://localhost:8080/lms/");
		//		タイトルが一致かどうか確認
		assertEquals("ログイン | LMS", webDriver.getTitle());
		//		スクショとる
		getEvidence(new Object() {
		}, "テスト02.1");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		// TODO ここに追加
		//		ログインIDを取得
		WebElement loginIdInput = webDriver.findElement(By.id("loginId"));
		//　　　ログインIDをクリック
		loginIdInput.click();
		//　　　ログインIDの値を入力
		loginIdInput.sendKeys("unregistered_user");

		//		パスワードを取得
		WebElement passwordInput = webDriver.findElement(By.id("password"));
		//		パスワードをクリック
		passwordInput.clear();
		//		パスワードの値を入力
		passwordInput.sendKeys("password123");
		//ボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();

		//		エラーメッセージが一致かどうか確認
		WebElement errorMessage = webDriver.findElement(By.cssSelector("span.help-inline.error"));
		assertEquals("* ログインに失敗しました。", errorMessage.getText());
		//		スクショとる
		getEvidence(new Object() {
		}, "テスト02.2");
	}

}
