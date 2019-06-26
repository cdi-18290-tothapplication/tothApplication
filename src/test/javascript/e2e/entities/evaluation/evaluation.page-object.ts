import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class EvaluationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-evaluation div table .btn-danger'));
  title = element.all(by.css('jhi-evaluation div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class EvaluationUpdatePage {
  pageTitle = element(by.id('jhi-evaluation-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  dateInput = element(by.id('field_date'));
  noteInput = element(by.id('field_note'));
  commentaryInput = element(by.id('field_commentary'));
  studientSelect = element(by.id('field_studient'));
  trainerSelect = element(by.id('field_trainer'));
  formationSessionSelect = element(by.id('field_formationSession'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDateInput(date) {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput() {
    return await this.dateInput.getAttribute('value');
  }

  async setNoteInput(note) {
    await this.noteInput.sendKeys(note);
  }

  async getNoteInput() {
    return await this.noteInput.getAttribute('value');
  }

  async setCommentaryInput(commentary) {
    await this.commentaryInput.sendKeys(commentary);
  }

  async getCommentaryInput() {
    return await this.commentaryInput.getAttribute('value');
  }

  async studientSelectLastOption(timeout?: number) {
    await this.studientSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async studientSelectOption(option) {
    await this.studientSelect.sendKeys(option);
  }

  getStudientSelect(): ElementFinder {
    return this.studientSelect;
  }

  async getStudientSelectedOption() {
    return await this.studientSelect.element(by.css('option:checked')).getText();
  }

  async trainerSelectLastOption(timeout?: number) {
    await this.trainerSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async trainerSelectOption(option) {
    await this.trainerSelect.sendKeys(option);
  }

  getTrainerSelect(): ElementFinder {
    return this.trainerSelect;
  }

  async getTrainerSelectedOption() {
    return await this.trainerSelect.element(by.css('option:checked')).getText();
  }

  async formationSessionSelectLastOption(timeout?: number) {
    await this.formationSessionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async formationSessionSelectOption(option) {
    await this.formationSessionSelect.sendKeys(option);
  }

  getFormationSessionSelect(): ElementFinder {
    return this.formationSessionSelect;
  }

  async getFormationSessionSelectedOption() {
    return await this.formationSessionSelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class EvaluationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-evaluation-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-evaluation'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
