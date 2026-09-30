package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
		//		タイトルが一致か検証
		assertEquals("ログイン | LMS", webDriver.getTitle());
		//		スクショとる
		getEvidence(new Object() {
		}, "テスト09.1");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加
		//		ログインIDを取得
		WebElement loginIdInput = webDriver.findElement(By.id("loginId"));
		//　　　ログインIDをクリック
		loginIdInput.click();
		//　　　ログインIDの値を入力
		loginIdInput.sendKeys("StudentAA01");

		//		パスワードを取得
		WebElement passwordInput = webDriver.findElement(By.id("password"));
		//		パスワードをクリック
		passwordInput.clear();
		//		パスワードの値を入力
		passwordInput.sendKeys("Aa123456789");
		//　　　ボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();
		//		タイトルが一致か検証
		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		//		スクショとる
		getEvidence(new Object() {
		}, "テスト09.2");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		// 「ようこそ」を含むリンク要素を取得
		WebElement welcomElement = webDriver.findElement(By.partialLinkText("ようこそ"));

		// リンクをクリック
		welcomElement.click();

		// タイトルが一致か検証
		assertEquals("ユーザー詳細", webDriver.getTitle());

		// スクショとる
		getEvidence(new Object() {
		}, "テスト09.3");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 画面をスクロール
		scrollBy("800");

		// 明示的待機オブジェクトの生成（最大10秒）
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

		// 週報のレポート行にある「修正する」ボタンのロケータ定義
		By detailButtonLocator = By
				.xpath("//h3[contains(text(),'レポート')]/following::table//tr[contains(., '週報')]//input[@value='修正する']");

		// 要素が存在・表示されるまで待機して取得
		WebElement detailButton = wait.until(ExpectedConditions.presenceOfElementLocated(detailButtonLocator));

		// JavaScriptで確実にクリック
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", detailButton);

		// タイトルが一致か検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// スクショとる
		getEvidence(new Object() {
		}, "テスト09.4");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		// 明示的待機オブジェクトの生成（最大10秒）
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

		// 学習項目の入力エリアが表示されるまで待機してクリア
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("intFieldName_0")));
		element.clear();

		// 画面をスクロール
		scrollBy("400");

		// 「提出する」ボタンをクリック
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		// 学習項目の入力エリアにエラー用クラスが付与されるまで待機
		wait.until(ExpectedConditions.attributeContains(By.id("intFieldName_0"), "class", "errorInput"));

		// エラー用のクラス名（errorInput）が付与されているか検証
		String className = webDriver.findElement(By.id("intFieldName_0")).getAttribute("class");
		assertTrue(className.contains("errorInput"));

		// スクショとる
		getEvidence(new Object() {
		}, "テスト09.5");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		// 明示的待機オブジェクトの生成（最大10秒）
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

		// 学習項目を入力
		WebElement element = webDriver.findElement(By.id("intFieldName_0"));
		element.clear();
		element.sendKeys("3");

		// 理解度の内容をクリア（HOMEキー送信による先頭移動・入力操作準備）
		WebElement beforeIntFieldValue = webDriver.findElement(By.id("intFieldValue_0"));
		beforeIntFieldValue.sendKeys(Keys.HOME);

		// 画面をスクロール
		scrollBy("400");

		// 「提出する」ボタンをクリック
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		// 画面再描画を待機
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("intFieldName_0")));

		// 理解度の入力エリアにエラー用のクラス名（errorInput）が付与されているか検証
		WebElement afterIntFieldValue = webDriver.findElement(By.id("intFieldValue_0"));
		assertTrue(afterIntFieldValue.getAttribute("class").contains("errorInput"));

		// スクショとる
		getEvidence(new Object() {
		}, "テスト09.6");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		// 明示的待機オブジェクトの生成（最大10秒）
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

		// 理解度のカーソル位置調整
		WebElement beforeIntFieldValue = webDriver.findElement(By.id("intFieldValue_0"));
		beforeIntFieldValue.sendKeys(Keys.END);

		// 目標の達成度に数値以外のテキストを入力
		WebElement beforeInputTextGoal = webDriver.findElement(By.id("content_0"));
		beforeInputTextGoal.clear();
		beforeInputTextGoal.sendKeys("報告内容を修正しました。");

		// 画面をスクロール
		scrollBy("400");

		// 「提出する」ボタンをクリック
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		// 画面再描画を待機
		beforeInputTextGoal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("intFieldName_0")));

		// 目標の達成度の入力エリアにエラー用のクラス名（errorInput）が付与されているか検証
		WebElement afterInputTextGoal = webDriver.findElement(By.id("content_0"));
		assertTrue(afterInputTextGoal.getAttribute("class").contains("errorInput"));

		// スクショとる
		getEvidence(new Object() {
		}, "テスト09.7");
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		// 明示的待機オブジェクトの生成（最大10秒）
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

		// 目標の達成度に範囲外の数値（100）を入力
		WebElement beforeInputTextGoal = webDriver.findElement(By.id("content_0"));
		beforeInputTextGoal.clear();
		beforeInputTextGoal.sendKeys("100");

		// 画面をスクロール
		scrollBy("400");

		// 「提出する」ボタンをクリック
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		// 画面再描画を待機
		beforeInputTextGoal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("intFieldName_0")));

		// 目標の達成度の入力エリアにエラー用のクラス名（errorInput）が付与されているか検証
		WebElement afterInputTextGoal = webDriver.findElement(By.id("content_0"));
		assertTrue(afterInputTextGoal.getAttribute("class").contains("errorInput"));

		// スクショとる
		getEvidence(new Object() {
		}, "テスト09.8");
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		// 明示的待機オブジェクトの生成（最大10秒）
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

		// 目標の達成度をクリア
		WebElement beforeInputTextGoal = webDriver.findElement(By.id("content_0"));
		beforeInputTextGoal.clear();

		// 所感をクリア
		WebElement beforeInputTextImpression = webDriver.findElement(By.id("content_1"));
		beforeInputTextImpression.clear();

		// 画面をスクロール
		scrollBy("400");

		// 「提出する」ボタンをクリック
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		// 画面再描画を待機
		beforeInputTextGoal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("intFieldName_0")));

		// 目標の達成度の入力エリアにエラー用クラスが付与されるまで待機して検証
		wait.until(ExpectedConditions.attributeContains(By.id("content_0"), "class", "errorInput"));
		WebElement afterInputTextGoal = webDriver.findElement(By.id("content_0"));
		assertTrue(afterInputTextGoal.getAttribute("class").contains("errorInput"));

		// 所感の入力エリアにエラー用クラスが付与されるまで待機して検証
		wait.until(ExpectedConditions.attributeContains(By.id("content_1"), "class", "errorInput"));
		WebElement afterInputTextImpression = webDriver.findElement(By.id("content_1"));
		assertTrue(afterInputTextImpression.getAttribute("class").contains("errorInput"));

		// 画面をスクロール
		scrollBy("300");

		// スクショとる
		getEvidence(new Object() {
		}, "テスト09.9");
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		// 明示的待機オブジェクトの生成（最大10秒）
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

		// 目標の達成度を入力
		WebElement beforeInputTextGoal = webDriver.findElement(By.id("content_0"));
		beforeInputTextGoal.clear();
		beforeInputTextGoal.sendKeys("4");

		// 所感を一旦設定
		WebElement beforeInputTextImpression = webDriver.findElement(By.id("content_1"));
		beforeInputTextImpression.clear();
		beforeInputTextImpression.sendKeys("所感を修正しました。");

		// 2002文字の文字列を生成して所感と振り返りに入力
		String textOver2000 = "あ".repeat(2002);
		WebElement beforeInputTextOverImpression = webDriver.findElement(By.id("content_1"));
		beforeInputTextOverImpression.clear();
		beforeInputTextOverImpression.sendKeys(textOver2000);

		WebElement beforeInputTextOverReview = webDriver.findElement(By.id("content_2"));
		beforeInputTextOverReview.clear();
		beforeInputTextOverReview.sendKeys(textOver2000);

		// 画面をスクロール
		scrollBy("400");

		// 「提出する」ボタンをクリック
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		// 画面再描画を待機
		beforeInputTextGoal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("intFieldName_0")));

		// 画面をスクロール
		scrollBy("400");

		// 所感および振り返りの入力エリアにエラー用のクラス名（errorInput）が付与されているか検証
		WebElement afterInputTextGoal = webDriver.findElement(By.id("content_1"));
		assertTrue(afterInputTextGoal.getAttribute("class").contains("errorInput"));

		WebElement afterInputTextReview = webDriver.findElement(By.id("content_2"));
		assertTrue(afterInputTextReview.getAttribute("class").contains("errorInput"));

		// スクショとる
		getEvidence(new Object() {
		}, "テスト09.10");
	}

}
